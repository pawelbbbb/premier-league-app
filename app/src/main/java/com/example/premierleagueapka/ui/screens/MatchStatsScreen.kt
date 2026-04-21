package com.example.premierleagueapka.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.premierleagueapka.data.local.database.DatabaseProvider
import com.example.premierleagueapka.viewmodel.MatchesViewModel


@Composable
fun MatchstatsScreen(
    navController: NavController,
    matchId: Int
) {

    val context = LocalContext.current
    val db = DatabaseProvider.getDatabase(context)
    val viewModel = remember { MatchesViewModel(db) }

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    val match = viewModel.matches.find { it.id == matchId }


    Box(modifier = Modifier.fillMaxSize()) {

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)
        ) {
            Text("Wróć")
        }
        Column {

            Text("Statystyki meczu")

            match?.let {

                Text("${it.homeTeam} ${it.homeScore} - ${it.awayScore} ${it.awayTeam}")

                Text("Posiadanie: ${it.possessionHome}% - ${it.possessionAway}%")
                Text("Strzały: ${it.shotsHome} - ${it.shotsAway}")
            }
        }
    }
}