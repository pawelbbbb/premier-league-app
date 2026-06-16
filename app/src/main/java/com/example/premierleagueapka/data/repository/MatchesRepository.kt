package com.example.premierleagueapka.data.repository

import com.example.premierleagueapka.data.local.database.AppDatabase
import com.example.premierleagueapka.data.local.entity.MatchEntity
import com.example.premierleagueapka.data.local.entity.TeamEntity
import com.example.premierleagueapka.data.remote.ApiMatch
import com.example.premierleagueapka.data.remote.FootballApi
import com.google.gson.JsonElement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.abs

class MatchesRepository(
    private val db: AppDatabase,
    private val api: FootballApi
) {
    suspend fun refreshAndLoad(): MatchesLoadResult = withContext(Dispatchers.IO) {
        val local = db.matchDao().getAll()
        if (local.isNotEmpty()) {
            return@withContext MatchesLoadResult(
                matches = local,
                source = "Room lokalnie",
                error = null
            )
        }

        val apiResult = runCatching {
            val remote = api.getPremierLeagueMatches()
                .matches
                .mapIndexedNotNull { index, match ->
                    val home = match.team1 ?: return@mapIndexedNotNull null
                    val away = match.team2 ?: return@mapIndexedNotNull null
                    val score = match.fullTimeScore()
                    val homeScore = score?.first ?: -1
                    val awayScore = score?.second ?: -1
                    val round = match.round?.filter { it.isDigit() }?.toIntOrNull() ?: 1

                    MatchEntity(
                        id = index + 1,
                        homeTeam = home,
                        awayTeam = away,
                        homeScore = homeScore,
                        awayScore = awayScore,
                        round = round.coerceAtLeast(1),
                        possessionHome = 45 + abs((home.hashCode() + index) % 20),
                        possessionAway = 55 - abs((home.hashCode() + index) % 20),
                        shotsHome = 6 + abs((home.hashCode() + index) % 12),
                        shotsAway = 6 + abs((away.hashCode() + index) % 12)
                    )
                }

            if (remote.isNotEmpty()) {
                db.matchDao().deleteAll()
                db.matchDao().insertAll(remote)
                saveTable(remote)
            }
            remote.size
        }

        val saved = db.matchDao().getAll()
        val source = when {
            apiResult.getOrNull()?.let { it > 0 } == true -> "API -> Room (${apiResult.getOrNull()} meczów)"
            saved.isNotEmpty() -> "Room lokalnie"
            else -> "Brak danych"
        }

        MatchesLoadResult(
            matches = saved,
            source = source,
            error = apiResult.exceptionOrNull()?.message
        )
    }

    suspend fun refreshTable(): List<TeamEntity> = withContext(Dispatchers.IO) {
        val localTeams = db.teamDao().getAll()
        if (localTeams.isNotEmpty()) {
            return@withContext localTeams
        }

        val localMatches = db.matchDao().getAll()
        if (localMatches.isNotEmpty()) {
            val table = buildTable(localMatches)
            db.teamDao().insertAll(table)
            return@withContext db.teamDao().getAll()
        }

        val result = refreshAndLoad()
        if (result.matches.isEmpty()) {
            emptyList()
        } else {
            val table = buildTable(result.matches)
            db.teamDao().deleteAll()
            db.teamDao().insertAll(table)
            db.teamDao().getAll()
        }
    }

    private suspend fun saveTable(matches: List<MatchEntity>) {
        db.teamDao().deleteAll()
        db.teamDao().insertAll(buildTable(matches))
    }

    private fun buildTable(matches: List<MatchEntity>): List<TeamEntity> {
        val points = linkedMapOf<String, Int>()
        val goalBalance = linkedMapOf<String, Int>()
        matches.forEach { match ->
            points.putIfAbsent(match.homeTeam, 0)
            points.putIfAbsent(match.awayTeam, 0)
            goalBalance.putIfAbsent(match.homeTeam, 0)
            goalBalance.putIfAbsent(match.awayTeam, 0)

            if (match.homeScore < 0 || match.awayScore < 0) {
                return@forEach
            }

            goalBalance[match.homeTeam] = goalBalance.getValue(match.homeTeam) + match.homeScore - match.awayScore
            goalBalance[match.awayTeam] = goalBalance.getValue(match.awayTeam) + match.awayScore - match.homeScore

            when {
                match.homeScore > match.awayScore -> points[match.homeTeam] = points.getValue(match.homeTeam) + 3
                match.homeScore < match.awayScore -> points[match.awayTeam] = points.getValue(match.awayTeam) + 3
                else -> {
                    points[match.homeTeam] = points.getValue(match.homeTeam) + 1
                    points[match.awayTeam] = points.getValue(match.awayTeam) + 1
                }
            }
        }

        return points
            .map { TeamEntity(name = it.key, points = it.value, goalBalance = goalBalance.getValue(it.key)) }
            .sortedWith(compareByDescending<TeamEntity> { it.points }.thenByDescending { it.goalBalance })
    }

    private fun ApiMatch.fullTimeScore(): Pair<Int, Int>? {
        return score?.scoreArray()?.let { values ->
            if (values.size >= 2) values[0] to values[1] else null
        }
    }

    private fun JsonElement.scoreArray(): List<Int>? {
        return when {
            isJsonArray -> asJsonArray.mapNotNull { value ->
                if (value.isJsonNull) null else value.asInt
            }
            isJsonObject -> asJsonObject.get("ft")?.scoreArray()
            else -> null
        }
    }
}

data class MatchesLoadResult(
    val matches: List<MatchEntity>,
    val source: String,
    val error: String?
)
