package com.example.premierleagueapka.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premierleagueapka.data.local.database.AppDatabase
import com.example.premierleagueapka.data.local.entity.TeamEntity
import kotlinx.coroutines.launch


class TableViewModel(
    private val db: AppDatabase
) : ViewModel() {

    var teams = mutableStateListOf<TeamEntity>()
        private set

    fun load() {
        viewModelScope.launch {

            val existing = db.teamDao().getAll()

            if (existing.isEmpty()) {
                db.teamDao().insert(TeamEntity(name = "Liverpool", points = 95))
                db.teamDao().insert(TeamEntity(name = "Manchester City", points = 67))
                db.teamDao().insert(TeamEntity(name = "Chelsea", points = 67))
                db.teamDao().insert(TeamEntity(name = "Tottenham", points = 67))
                db.teamDao().insert(TeamEntity(name = "Newcastle", points = 67))
                db.teamDao().insert(TeamEntity(name = "Aston Villa", points = 64))
                db.teamDao().insert(TeamEntity(name = "Brighton", points = 60))
                db.teamDao().insert(TeamEntity(name = "West Ham", points = 58))
                db.teamDao().insert(TeamEntity(name = "Wolves", points = 55))
                db.teamDao().insert(TeamEntity(name = "Crystal Palace", points = 52))
                db.teamDao().insert(TeamEntity(name = "Brentford", points = 50))
                db.teamDao().insert(TeamEntity(name = "Fulham", points = 48))
                db.teamDao().insert(TeamEntity(name = "Bournemouth", points = 45))
                db.teamDao().insert(TeamEntity(name = "Nottingham Forest", points = 42))
                db.teamDao().insert(TeamEntity(name = "Sunderland", points = 40))
                db.teamDao().insert(TeamEntity(name = "Leeds", points = 38))
                db.teamDao().insert(TeamEntity(name = "Burnley", points = 36))
                db.teamDao().insert(TeamEntity(name = "Manchester United", points = 30))
                db.teamDao().insert(TeamEntity(name = "Everton", points = 28))
                db.teamDao().insert(TeamEntity(name = "Arsenal", points = 20))
            }

            teams.clear()
            teams.addAll(db.teamDao().getAll())
        }
    }
}