package com.example.premierleagueapka.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premierleagueapka.data.local.database.AppDatabase
import com.example.premierleagueapka.data.local.entity.PlayerStatsEntity
import kotlinx.coroutines.launch


class PlayerStatsViewModel(
    private val db: AppDatabase
) : ViewModel() {

    var players = mutableStateListOf<PlayerStatsEntity>()
        private set

    fun load() {
        viewModelScope.launch {

            val existing = db.playerStatsDao().getAll()

            if (existing.isEmpty()) {
                db.playerStatsDao().insert(PlayerStatsEntity(playerName = "Mohamed Salah", goals = 28, assists = 12))
                db.playerStatsDao().insert(PlayerStatsEntity(playerName = "Erling Haaland", goals = 26, assists = 5))
                db.playerStatsDao().insert(PlayerStatsEntity(playerName = "Dominik Szoboszlai", goals = 20, assists = 11))
                db.playerStatsDao().insert(PlayerStatsEntity(playerName = "Cole Palmer", goals = 19, assists = 9))
                db.playerStatsDao().insert(PlayerStatsEntity(playerName = "Alexander Isak", goals = 18, assists = 6))
                db.playerStatsDao().insert(PlayerStatsEntity(playerName = "Rio Ngumoha", goals = 17, assists = 8))
                db.playerStatsDao().insert(PlayerStatsEntity(playerName = "Phil Foden", goals = 16, assists = 10))
                db.playerStatsDao().insert(PlayerStatsEntity(playerName = "Jarrod Bowen", goals = 15, assists = 7))
                db.playerStatsDao().insert(PlayerStatsEntity(playerName = "Ollie Watkins", goals = 14, assists = 9))
                db.playerStatsDao().insert(PlayerStatsEntity(playerName = "Igor Thiago", goals = 13, assists = 6))
                db.playerStatsDao().insert(PlayerStatsEntity(playerName = "Bryan Mbuemo", goals = 12, assists = 5))
                db.playerStatsDao().insert(PlayerStatsEntity(playerName = "Łukasz Fabiański", goals = 12, assists = 4))
                db.playerStatsDao().insert(PlayerStatsEntity(playerName = "Micky van de Ven", goals = 12, assists = 2))
                db.playerStatsDao().insert(PlayerStatsEntity(playerName = "Bruno Fernandes", goals = 10, assists = 11))
                db.playerStatsDao().insert(PlayerStatsEntity(playerName = "James Maddison", goals = 9, assists = 10))
                db.playerStatsDao().insert(PlayerStatsEntity(playerName = "Trey Nyoni", goals = 8, assists = 12))
                db.playerStatsDao().insert(PlayerStatsEntity(playerName = "Virgil van Dijk", goals = 5, assists = 2))
                db.playerStatsDao().insert(PlayerStatsEntity(playerName = "Joe Gomez", goals = 4, assists = 3))
                db.playerStatsDao().insert(PlayerStatsEntity(playerName = "Eberechi Eze", goals = 7, assists = 6))
                db.playerStatsDao().insert(PlayerStatsEntity(playerName = "Kaoru Mitoma", goals = 6, assists = 7))
            }

            players.clear()
            players.addAll(db.playerStatsDao().getAll())
        }
    }
}