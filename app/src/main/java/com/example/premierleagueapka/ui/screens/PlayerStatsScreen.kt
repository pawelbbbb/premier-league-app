package com.example.premierleagueapka.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.premierleagueapka.data.local.database.DatabaseProvider
import com.example.premierleagueapka.ui.components.AppScreen
import com.example.premierleagueapka.ui.components.DividerLine
import com.example.premierleagueapka.viewmodel.PlayerStatsViewModel

@Composable
fun PlayerStatsScreen(navController: NavController) {

    val context = LocalContext.current
    val db = DatabaseProvider.getDatabase(context)
    val viewModel = remember { PlayerStatsViewModel(db) }

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    AppScreen("Bramki", navController) {
        if (viewModel.isLoading.value && viewModel.players.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(Modifier.fillMaxSize()) {
                item {
                    Row(Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                        Text("Zawodnik", Modifier.weight(2f), fontWeight = FontWeight.Bold)
                        Text("G", Modifier.weight(0.5f), fontWeight = FontWeight.Bold)
                        Text("A", Modifier.weight(0.5f), fontWeight = FontWeight.Bold)
                    }
                    DividerLine()
                }
                items(viewModel.players.sortedWith(compareByDescending<com.example.premierleagueapka.data.local.entity.PlayerStatsEntity> { it.goals }.thenByDescending { it.assists })) { player ->
                    Row(
                        Modifier.fillMaxWidth().padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(Modifier.weight(2f)) {
                            Text(player.playerName, style = MaterialTheme.typography.bodyMedium)
                            Text(player.teamName, style = MaterialTheme.typography.labelSmall)
                        }
                        Text(player.goals.toString(), Modifier.weight(0.5f))
                        Text(player.assists.toString(), Modifier.weight(0.5f))
                    }
                    DividerLine()
                }
            }
        }
    }
}
