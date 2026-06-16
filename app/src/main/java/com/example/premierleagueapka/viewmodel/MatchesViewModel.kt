package com.example.premierleagueapka.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premierleagueapka.data.local.database.AppDatabase
import com.example.premierleagueapka.data.local.entity.MatchEntity
import com.example.premierleagueapka.data.remote.FootballApiProvider
import com.example.premierleagueapka.data.repository.MatchesRepository
import kotlinx.coroutines.launch


class MatchesViewModel(
    private val db: AppDatabase
) : ViewModel() {

    private val repository = MatchesRepository(db, FootballApiProvider.api)
    private var hasLoaded = false

    var matches = mutableStateListOf<MatchEntity>()
        private set

    var isLoading = mutableStateOf(false)
        private set

    var dataSource = mutableStateOf("")
        private set

    var apiError = mutableStateOf<String?>(null)
        private set

    fun load() {
        if (hasLoaded) return
        hasLoaded = true

        viewModelScope.launch {
            isLoading.value = true
            matches.clear()
            val result = repository.refreshAndLoad()
            matches.addAll(result.matches)
            dataSource.value = result.source
            apiError.value = result.error
            isLoading.value = false
        }
    }
}
