package com.example.premierleagueapka.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.premierleagueapka.ui.screens.MatchesScreen
import com.example.premierleagueapka.ui.screens.MatchstatsScreen
import com.example.premierleagueapka.ui.screens.MediaScreen
import com.example.premierleagueapka.ui.screens.MenuScreen
import com.example.premierleagueapka.ui.screens.NewsScreen
import com.example.premierleagueapka.ui.screens.SettingsScreen
import com.example.premierleagueapka.ui.screens.PlayerStatsScreen
import com.example.premierleagueapka.ui.screens.TableScreen

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(navController, startDestination = "menu") {

        composable("menu") {
            MenuScreen(navController)
        }

        composable("table") {
            TableScreen(navController)
        }

        composable("matches") {
            MatchesScreen(navController)
        }

        composable(
            "match_stats/{matchId}",
            arguments = listOf(navArgument("matchId") { type = NavType.IntType })
        ) { backStackEntry ->
            val matchId = backStackEntry.arguments?.getInt("matchId") ?: 0
            MatchstatsScreen(navController, matchId)
        }

        composable("statistics") {
            PlayerStatsScreen(navController)
        }

        composable("newsy") {
            NewsScreen(navController)
        }

        composable("media") {
            MediaScreen(navController)
        }

        composable("settings") {
            SettingsScreen(navController)
        }
    }
}
