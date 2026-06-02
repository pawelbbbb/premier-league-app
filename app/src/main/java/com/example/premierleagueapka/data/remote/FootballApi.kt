package com.example.premierleagueapka.data.remote

import com.google.gson.JsonElement
import retrofit2.http.Header
import retrofit2.http.GET

interface FootballApi {
    @GET("2025-26/en.1.json")
    suspend fun getPremierLeagueMatches(): CompetitionResponse
}

data class CompetitionResponse(
    val matches: List<ApiMatch>
)

data class ApiMatch(
    val round: String?,
    val date: String?,
    val team1: String?,
    val team2: String?,
    val score: JsonElement?
)

interface FootballDataApi {
    @GET("v4/competitions/PL/scorers")
    suspend fun getPremierLeagueScorers(
        @Header("X-Auth-Token") token: String
    ): ScorersResponse
}

data class ScorersResponse(
    val scorers: List<ApiScorer>
)

data class ApiScorer(
    val player: ApiPlayer?,
    val team: ApiTeam?,
    val goals: Int?,
    val assists: Int?
)

data class ApiPlayer(
    val name: String?
)

data class ApiTeam(
    val name: String?,
    val shortName: String?,
    val tla: String?
)
