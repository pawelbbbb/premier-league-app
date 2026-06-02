package com.example.premierleagueapka.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.premierleagueapka.data.local.entity.TeamEntity


@Dao
interface TeamDao {

    @Insert
    suspend fun insert(team: TeamEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(teams: List<TeamEntity>)

    @Query("SELECT * FROM teams ORDER BY points DESC, name")
    suspend fun getAll(): List<TeamEntity>

    @Query("DELETE FROM teams")
    suspend fun deleteAll()
}
