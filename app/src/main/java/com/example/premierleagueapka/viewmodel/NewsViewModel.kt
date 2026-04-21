package com.example.premierleagueapka.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premierleagueapka.data.local.database.AppDatabase
import com.example.premierleagueapka.data.local.entity.NewsEntity
import kotlinx.coroutines.launch


class NewsViewModel(
    private val db: AppDatabase
) : ViewModel() {

    var news = mutableStateListOf<NewsEntity>()
        private set

    fun load() {
        viewModelScope.launch {

            val existing = db.newsDao().getAll()

            if (existing.isEmpty()) {
                db.newsDao().insert(
                    NewsEntity(
                        title = "Liverpool górą w derbach",
                        content = "Liverpool pokonał Manchester United 3:1 w emocjonującym meczu."
                    )
                )
                db.newsDao().insert(
                    NewsEntity(
                        title = "Tottenham zwycięża Arsenal",
                        content = "Tottenham wygrał 2:0 w starciu na północnym Londynie."
                    )
                )
                db.newsDao().insert(
                    NewsEntity(
                        title = "Manchester City przegrywa z Leeds",
                        content = "Leeds sensacyjnie pokonało Manchester City 2:1."
                    )
                )
                db.newsDao().insert(
                    NewsEntity(
                        title = "Chelsea remisuje z Newcastle",
                        content = "Chelsea i Newcastle podzielili się punktami 2:2."
                    )
                )
                db.newsDao().insert(
                    NewsEntity(
                        title = "Brighton pokonuje Wolves",
                        content = "Brighton zdobywa 3 punkty, wygrywając 2:1 z Wolves."
                    )
                )
                db.newsDao().insert(
                    NewsEntity(
                        title = "West Ham lepszy od Evertonu",
                        content = "West Ham pokonał Everton 2:1 w emocjonującym meczu."
                    )
                )
                db.newsDao().insert(
                    NewsEntity(
                        title = "Fulham wygrywa z Burnley",
                        content = "Fulham triumfuje 2:0 nad Burnley."
                    )
                )
                db.newsDao().insert(
                    NewsEntity(
                        title = "Bournemouth pokonuje Nottingham Forest",
                        content = "Bournemouth zdobywa trzy punkty, zwyciężając 2:1."
                    )
                )
                db.newsDao().insert(
                    NewsEntity(
                        title = "Chelsea przegrywa z Manchester United",
                        content = "Manchester United wygrywa 2:1 z Chelsea w meczu 2. kolejki."
                    )
                )
                db.newsDao().insert(
                    NewsEntity(
                        title = "Tottenham wygrywa z West Ham",
                        content = "Tottenham triumfuje 2:1 nad West Ham w drugim meczu sezonu."
                    )
                )
            }

            news.clear()
            news.addAll(db.newsDao().getAll())
        }
    }
}