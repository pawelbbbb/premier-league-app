package com.example.premierleagueapka.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premierleagueapka.data.local.database.AppDatabase
import com.example.premierleagueapka.data.local.entity.PlayerStatsEntity
import com.example.premierleagueapka.data.remote.FootballApiProvider
import com.example.premierleagueapka.data.repository.PlayerStatsRepository
import kotlinx.coroutines.launch


class PlayerStatsViewModel(
    private val db: AppDatabase
) : ViewModel() {

    private val repository = PlayerStatsRepository(db, FootballApiProvider.footballDataApi)

    var players = mutableStateListOf<PlayerStatsEntity>()
        private set

    var isLoading = mutableStateOf(false)
        private set

    var dataSource = mutableStateOf("")
        private set

    var apiError = mutableStateOf<String?>(null)
        private set

    fun load() {
        viewModelScope.launch {
            isLoading.value = true
            players.clear()
            val result = repository.refreshAndLoad()
            players.addAll(result.players)
            dataSource.value = result.source
            apiError.value = result.error
            isLoading.value = false
        }
    }
}
