package com.example.premierleagueapka.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.premierleagueapka.data.local.entity.NewsEntity


@Dao
interface NewsDao {

    @Insert
    suspend fun insert(news: NewsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<NewsEntity>)

    @Query("SELECT * FROM news ORDER BY id DESC")
    suspend fun getAll(): List<NewsEntity>

    @Query("SELECT * FROM news WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): NewsEntity?
}
