package com.example.premierleagueapka.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.example.premierleagueapka.data.local.entity.MatchEntity
import com.example.premierleagueapka.ui.components.AppScreen
import com.example.premierleagueapka.ui.components.TeamBadge
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


    AppScreen("Statystyki meczu", navController) {
        match?.let {
            Column(Modifier.fillMaxSize()) {
                Row(
                    Modifier.fillMaxWidth().padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TeamBadge(it.homeTeam, Modifier)
                    Text(matchScoreText(it), style = MaterialTheme.typography.headlineSmall)
                    TeamBadge(it.awayTeam, Modifier)
                }
                Text("${it.homeTeam} - ${it.awayTeam}", modifier = Modifier.align(Alignment.CenterHorizontally))
                Spacer(Modifier.height(24.dp))
                if (it.homeScore < 0 || it.awayScore < 0) {
                    Text("Mecz jeszcze nie został rozegrany.")
                } else {
                    StatLine("Posiadanie", it.possessionHome, it.possessionAway, "%")
                    StatLine("Strzały", it.shotsHome, it.shotsAway, "")
                }
            }
        } ?: Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Ładowanie meczu")
        }
    }
}

private fun matchScoreText(match: MatchEntity): String {
    return if (match.homeScore < 0 || match.awayScore < 0) "- - -" else "${match.homeScore} - ${match.awayScore}"
}

@Composable
private fun StatLine(label: String, home: Int, away: Int, suffix: String) {
    Column(Modifier.fillMaxWidth().padding(vertical = 10.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("$home$suffix")
            Text(label)
            Text("$away$suffix")
        }
        LinearProgressIndicator(
            progress = { home.toFloat() / (home + away).coerceAtLeast(1) },
            modifier = Modifier.fillMaxWidth().padding(top = 6.dp)
        )
    }
}
