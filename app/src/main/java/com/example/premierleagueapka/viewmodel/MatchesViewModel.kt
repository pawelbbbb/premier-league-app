package com.example.premierleagueapka.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premierleagueapka.data.local.database.AppDatabase
import com.example.premierleagueapka.data.local.entity.MatchEntity
import kotlinx.coroutines.launch


class MatchesViewModel(
    private val db: AppDatabase
) : ViewModel() {

    var matches = mutableStateListOf<MatchEntity>()
        private set

    fun load() {
        viewModelScope.launch {

            val existing = db.matchDao().getAll()

            if (existing.isEmpty()) {

                db.matchDao().insert(
                    MatchEntity(1, "Liverpool", "Manchester United", 3, 1, 1, 58, 42, 14, 9)
                )
                db.matchDao().insert(
                    MatchEntity(2, "Tottenham", "Arsenal", 2, 0, 1, 55, 45, 11, 7)
                )
                db.matchDao().insert(
                    MatchEntity(3, "Manchester City", "Leeds", 1, 2, 1, 65, 35, 15, 6)
                )
                db.matchDao().insert(
                    MatchEntity(4, "Chelsea", "Newcastle", 2, 2, 1, 50, 50, 10, 10)
                )
                db.matchDao().insert(
                    MatchEntity(5, "Aston Villa", "Brighton", 1, 1, 1, 52, 48, 9, 9)
                )
                db.matchDao().insert(
                    MatchEntity(6, "West Ham", "Everton", 2, 1, 1, 54, 46, 12, 8)
                )
                db.matchDao().insert(
                    MatchEntity(7, "Wolves", "Crystal Palace", 1, 0, 1, 49, 51, 8, 11)
                )
                db.matchDao().insert(
                    MatchEntity(8, "Brentford", "Fulham", 3, 2, 1, 53, 47, 13, 10)
                )
                db.matchDao().insert(
                    MatchEntity(9, "Bournemouth", "Nottingham Forest", 2, 1, 1, 51, 49, 10, 8)
                )
                db.matchDao().insert(
                    MatchEntity(10, "Burnley", "Sheffield United", 1, 1, 1, 48, 52, 7, 7)
                )

                db.matchDao().insert(
                    MatchEntity(11, "Manchester United", "Chelsea", 1, 2, 2, 46, 54, 9, 12)
                )
                db.matchDao().insert(
                    MatchEntity(12, "Arsenal", "Manchester City", 1, 3, 2, 44, 56, 8, 14)
                )
                db.matchDao().insert(
                    MatchEntity(13, "Leeds", "Liverpool", 0, 2, 2, 40, 60, 6, 13)
                )

                db.matchDao().insert(
                    MatchEntity(14, "Tottenham", "West Ham", 2, 1, 2, 57, 43, 12, 9)
                )
                db.matchDao().insert(
                    MatchEntity(15, "Newcastle", "Aston Villa", 1, 2, 2, 47, 53, 9, 11)
                )
                db.matchDao().insert(
                    MatchEntity(16, "Brighton", "Wolves", 2, 2, 2, 50, 50, 11, 11)
                )
                db.matchDao().insert(
                    MatchEntity(17, "Everton", "Brentford", 0, 1, 2, 45, 55, 7, 10)
                )
                db.matchDao().insert(
                    MatchEntity(18, "Crystal Palace", "Bournemouth", 1, 1, 2, 52, 48, 9, 9)
                )
                db.matchDao().insert(
                    MatchEntity(19, "Fulham", "Burnley", 2, 0, 2, 56, 44, 13, 6)
                )
                db.matchDao().insert(
                    MatchEntity(20, "Nottingham Forest", "Sheffield United", 1, 0, 2, 49, 51, 8, 7)
                )
            }

            matches.clear()
            matches.addAll(db.matchDao().getAll())
        }
    }
}