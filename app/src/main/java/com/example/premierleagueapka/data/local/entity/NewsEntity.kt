package com.example.premierleagueapka.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val imageName: String
)
