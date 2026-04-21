package com.example.premierleagueapka.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "matches")
data class MatchEntity(
    @PrimaryKey val id: Int,
    val homeTeam: String,
    val awayTeam: String,
    val homeScore: Int,
    val awayScore: Int,
    val round: Int,
    val possessionHome: Int,
    val possessionAway: Int,
    val shotsHome: Int,
    val shotsAway: Int
)