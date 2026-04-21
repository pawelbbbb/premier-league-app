package com.example.premierleagueapka.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.premierleagueapka.data.local.database.DatabaseProvider
import com.example.premierleagueapka.viewmodel.MatchesViewModel

@Composable
fun MatchesScreen(navController: NavController) {

    val context = LocalContext.current
    val db = DatabaseProvider.getDatabase(context)
    val viewModel = remember { MatchesViewModel(db) }

    var selectedRound by remember { mutableStateOf(1) }

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Mecze i wyniki", style = MaterialTheme.typography.headlineSmall)
            Button(onClick = { navController.popBackStack() }) {
                Text("Wróć")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            (1..2).forEach { round ->
                Button(
                    onClick = { selectedRound = round },
                    colors = if (selectedRound == round) ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ) else ButtonDefaults.buttonColors()
                ) {
                    Text("Kolejka $round")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(viewModel.matches.filter { it.round == selectedRound }) { match ->
                Button(
                    onClick = { navController.navigate("match_stats/${match.id}") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("${match.homeTeam} ${match.homeScore} : ${match.awayScore} ${match.awayTeam}")
                }
            }
        }
    }
}