package com.example.premierleagueapka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.premierleagueapka.navigation.NavGraph
import com.example.premierleagueapka.ui.theme.PremierLeagueApkaTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PremierLeagueApkaTheme(dynamicColor = false) {
                val navController = rememberNavController()

                NavGraph(navController)
            }
        }
    }
}
