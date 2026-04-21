package com.example.premierleagueapka.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.premierleagueapka.viewmodel.PlayerStatsViewModel

@Composable
fun PlayerStatsScreen(navController: NavController) {

    val context = LocalContext.current
    val db = DatabaseProvider.getDatabase(context)
    val viewModel = remember { PlayerStatsViewModel(db) }

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)
        ) {
            Text("Wróć")
        }

        Column {

            Text("Statystyki zawodników")

            LazyColumn {
                items(viewModel.players) { player ->
                    Text(
                        "${player.playerName} - ${player.goals} goli (${player.assists} asyst)",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}