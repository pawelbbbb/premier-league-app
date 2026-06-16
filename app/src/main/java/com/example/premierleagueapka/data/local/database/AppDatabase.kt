package com.example.premierleagueapka.data.local.database

import android.annotation.SuppressLint
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.premierleagueapka.data.local.dao.TeamDao
import com.example.premierleagueapka.data.local.entity.TeamEntity
import com.example.premierleagueapka.data.local.dao.MatchDao
import com.example.premierleagueapka.data.local.entity.MatchEntity
import com.example.premierleagueapka.data.local.dao.PlayerStatsDao
import com.example.premierleagueapka.data.local.entity.PlayerStatsEntity
import com.example.premierleagueapka.data.local.dao.NewsDao
import com.example.premierleagueapka.data.local.entity.NewsEntity

@SuppressLint("RestrictedApi")
@Database(
    entities = [
        TeamEntity::class,
        MatchEntity::class,
        PlayerStatsEntity::class,
        NewsEntity::class
    ],
    version = 7
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun teamDao(): TeamDao
    abstract fun matchDao(): MatchDao
    abstract fun playerStatsDao(): PlayerStatsDao
    abstract fun newsDao(): NewsDao
}
