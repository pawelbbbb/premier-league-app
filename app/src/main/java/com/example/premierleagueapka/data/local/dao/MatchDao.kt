package com.example.premierleagueapka.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.premierleagueapka.data.local.entity.MatchEntity


@Dao
interface MatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(match: MatchEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(matches: List<MatchEntity>)

    @Query("SELECT * FROM matches ORDER BY round, id")
    suspend fun getAll(): List<MatchEntity>

    @Query("SELECT * FROM matches WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): MatchEntity?

    @Query("DELETE FROM matches")
    suspend fun deleteAll()
}
