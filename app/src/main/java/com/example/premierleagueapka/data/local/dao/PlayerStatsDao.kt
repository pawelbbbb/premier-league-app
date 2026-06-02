package com.example.premierleagueapka.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.premierleagueapka.data.local.entity.PlayerStatsEntity


@Dao
interface PlayerStatsDao {

    @Insert
    suspend fun insert(stat: PlayerStatsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stats: List<PlayerStatsEntity>)

    @Query("SELECT * FROM player_stats ORDER BY goals DESC, assists DESC, playerName")
    suspend fun getAll(): List<PlayerStatsEntity>

    @Query("DELETE FROM player_stats")
    suspend fun deleteAll()
}
