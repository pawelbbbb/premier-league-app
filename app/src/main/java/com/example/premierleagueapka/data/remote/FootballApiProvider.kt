package com.example.premierleagueapka.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FootballApiProvider {
    val api: FootballApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/openfootball/football.json/master/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FootballApi::class.java)
    }

    val footballDataApi: FootballDataApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.football-data.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FootballDataApi::class.java)
    }
}
