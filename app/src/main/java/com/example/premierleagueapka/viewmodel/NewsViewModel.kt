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

    private var hasLoaded = false

    var news = mutableStateListOf<NewsEntity>()
        private set

    fun load() {
        if (hasLoaded) return
        hasLoaded = true

        viewModelScope.launch {

            val existing = db.newsDao().getAll()

            if (existing.isEmpty()) {
                db.newsDao().insertAll(
                    listOf(
                        NewsEntity(
                            title = "Senesi nowym, nabytkiem Tottenhamu",
                            content = "Marcos Senesi przechodzi z AFC Bournemouth do Tottenhamu za kwotę 25 mln Euro. Argentyńczyk był jednym z najlepszych obrońców tego sezonu PL, po ogromnym zainteresowaniu klubów z czołówki Senesi wybrał Tottenham",
                            imageName = "senesi"
                        ),
                        NewsEntity(
                            title = "Arsenal Przegrywa!!",
                            content = "Arsenal przegrywa w Finale Ligi Mistrzów. Francuskie Paris Saint-Germain okazało się zbyt mocne, mecz został rostrzygnięty w rzutach karnych w których Paryżanie triumfowali 4:3",
                            imageName = "psg"
                        ),
                        NewsEntity(
                            title = "Guardiola pożegnany",
                            content = "Po latach sukcesów oraz wygraniu wielu trofeów Manchester City żegna Pepa Guardiole jako swojego trenera, jego następcą zostaje Enzo Maresca",
                            imageName = "guardiola"
                        ),
                        NewsEntity(
                            title = "Znamy wszyskich reprezentantów w europejskich pucharach",
                            content = "Znamy wszystkie kluby które zagrają w europejskich pucharach w sezonie 26/27, będą nimi:\nLiga Mistrzów: Arsenal, Manchester City, Manchester United, Aston Villa oraz Liverpool\nLiga Europy: Sunderland, AFC Bournemouth, Crystal Palace\nLiga Konferencji: Brighton and Hove Albion",
                            imageName = "europa"
                        )
                    )
                )
            }

            news.clear()
            news.addAll(db.newsDao().getAll())
        }
    }
}
