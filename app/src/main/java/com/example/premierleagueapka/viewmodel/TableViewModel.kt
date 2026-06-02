package com.example.premierleagueapka.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premierleagueapka.data.local.database.AppDatabase
import com.example.premierleagueapka.data.local.entity.TeamEntity
import com.example.premierleagueapka.data.remote.FootballApiProvider
import com.example.premierleagueapka.data.repository.MatchesRepository
import kotlinx.coroutines.launch


class TableViewModel(
    private val db: AppDatabase
) : ViewModel() {

    private val repository = MatchesRepository(db, FootballApiProvider.api)

    var teams = mutableStateListOf<TeamEntity>()
        private set

    fun load() {
        viewModelScope.launch {
            teams.clear()
            teams.addAll(repository.refreshTable())
        }
    }
}
