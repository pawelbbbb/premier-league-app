package com.example.premierleagueapka.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.premierleagueapka.data.local.entity.NewsEntity


@Dao
interface NewsDao {

    @Insert
    suspend fun insert(news: NewsEntity)

    @Query("SELECT * FROM news")
    suspend fun getAll(): List<NewsEntity>
}