package com.example.premierleagueapka.data.repository

import com.example.premierleagueapka.BuildConfig
import com.example.premierleagueapka.data.local.database.AppDatabase
import com.example.premierleagueapka.data.local.entity.PlayerStatsEntity
import com.example.premierleagueapka.data.remote.FootballDataApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlayerStatsRepository(
    private val db: AppDatabase,
    private val api: FootballDataApi
) {
    suspend fun refreshAndLoad(): PlayerStatsLoadResult = withContext(Dispatchers.IO) {
        val local = db.playerStatsDao().getAll()
        if (local.isNotEmpty()) {
            return@withContext PlayerStatsLoadResult(
                players = local,
                source = "Room lokalnie",
                error = null
            )
        }

        val token = BuildConfig.FOOTBALL_DATA_API_KEY
        val apiResult = if (token.isBlank()) {
            Result.failure(IllegalStateException("Brak FOOTBALL_DATA_API_KEY w local.properties"))
        } else {
            runCatching {
                val remote = api.getPremierLeagueScorers(token)
                    .scorers
                    .mapNotNull { scorer ->
                        val playerName = scorer.player?.name ?: return@mapNotNull null
                        val teamName = scorer.team?.shortName
                            ?: scorer.team?.name
                            ?: scorer.team?.tla
                            ?: "Premier League"

                        PlayerStatsEntity(
                            playerName = playerName,
                            teamName = teamName,
                            goals = scorer.goals ?: 0,
                            assists = scorer.assists ?: 0
                        )
                    }

                if (remote.isNotEmpty()) {
                    db.playerStatsDao().deleteAll()
                    db.playerStatsDao().insertAll(remote)
                }
                remote.size
            }
        }

        val saved = db.playerStatsDao().getAll()

        PlayerStatsLoadResult(
            players = saved,
            source = when {
                apiResult.getOrNull()?.let { it > 0 } == true -> "football-data.org -> Room (${apiResult.getOrNull()} strzelców)"
                saved.isNotEmpty() -> "Room lokalnie"
                else -> "Brak danych"
            },
            error = apiResult.exceptionOrNull()?.message
        )
    }
}

data class PlayerStatsLoadResult(
    val players: List<PlayerStatsEntity>,
    val source: String,
    val error: String?
)
