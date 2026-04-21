package com.example.premierleagueapka.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.premierleagueapka.data.local.entity.MatchEntity


@Dao
interface MatchDao {

    @Insert
    suspend fun insert(match: MatchEntity)

    @Query("SELECT * FROM matches")
    suspend fun getAll(): List<MatchEntity>
}