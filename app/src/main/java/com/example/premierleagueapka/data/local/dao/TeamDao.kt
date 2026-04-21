package com.example.premierleagueapka.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.premierleagueapka.data.local.entity.TeamEntity


@Dao
interface TeamDao {

    @Insert
    suspend fun insert(team: TeamEntity)

    @Query("SELECT * FROM teams")
    suspend fun getAll(): List<TeamEntity>
}