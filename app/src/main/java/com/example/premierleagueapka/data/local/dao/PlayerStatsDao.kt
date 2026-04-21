package com.example.premierleagueapka.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.premierleagueapka.data.local.entity.PlayerStatsEntity


@Dao
interface PlayerStatsDao {

    @Insert
    suspend fun insert(stat: PlayerStatsEntity)

    @Query("SELECT * FROM player_stats")
    suspend fun getAll(): List<PlayerStatsEntity>
}