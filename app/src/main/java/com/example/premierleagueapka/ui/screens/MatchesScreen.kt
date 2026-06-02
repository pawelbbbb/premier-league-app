package com.example.premierleagueapka.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.premierleagueapka.data.local.database.DatabaseProvider
import com.example.premierleagueapka.data.local.entity.MatchEntity
import com.example.premierleagueapka.ui.components.AppScreen
import com.example.premierleagueapka.ui.components.TeamBadge
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

    AppScreen("Mecze i wyniki", navController) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items((1..38).toList()) { round ->
                    FilterChip(
                        selected = selectedRound == round,
                        onClick = { selectedRound = round },
                            label = { Text(round.toString()) }
                    )
                    }
                }
            }

            if (viewModel.isLoading.value && viewModel.matches.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                val roundMatches = viewModel.matches.filter { it.round == selectedRound }
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        Text(
                            "Kolejka $selectedRound",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                    items(roundMatches) { match ->
                        MatchRow(match) { navController.navigate("match_stats/${match.id}") }
                    }
                }
            }
        }
    }
}

@Composable
private fun MatchRow(match: MatchEntity, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        tonalElevation = 1.dp,
        shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TeamBadge(match.homeTeam)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    matchScore(match),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    "${match.homeTeam.take(12)} vs ${match.awayTeam.take(12)}",
                    style = MaterialTheme.typography.labelSmall
                )
            }
            TeamBadge(match.awayTeam)
        }
    }
}

private fun matchScore(match: MatchEntity): String {
    return if (match.homeScore < 0 || match.awayScore < 0) {
        "-  -"
    } else {
        "${match.homeScore}  -  ${match.awayScore}"
    }
}
