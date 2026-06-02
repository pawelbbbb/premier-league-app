package com.example.premierleagueapka.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "player_stats")
data class PlayerStatsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val playerName: String,
    val teamName: String,
    val goals: Int,
    val assists: Int
)
