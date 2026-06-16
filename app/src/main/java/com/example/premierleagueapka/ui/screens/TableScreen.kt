package com.example.premierleagueapka.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.premierleagueapka.data.local.database.DatabaseProvider
import com.example.premierleagueapka.data.local.entity.TeamEntity
import com.example.premierleagueapka.ui.components.AppScreen
import com.example.premierleagueapka.ui.components.DividerLine
import com.example.premierleagueapka.ui.components.TeamBadge
import com.example.premierleagueapka.viewmodel.TableViewModel

@Composable
fun TableScreen(navController: NavController) {

    val context = LocalContext.current
    val db = DatabaseProvider.getDatabase(context)

    val viewModel = remember {
        TableViewModel(db)
    }
    val sortedTeams = remember {
        derivedStateOf {
            viewModel.teams
                .sortedWith(compareByDescending<TeamEntity> { it.points }.thenByDescending { it.goalBalance })
                .take(20)
                .withIndex()
                .toList()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    AppScreen("Tabela", navController) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Row(Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                    Text("#", Modifier.weight(0.4f), fontWeight = FontWeight.Bold)
                    Text("Klub", Modifier.weight(1.8f), fontWeight = FontWeight.Bold)
                    Text("BB", Modifier.weight(0.5f), fontWeight = FontWeight.Bold)
                    Text("Pkt", Modifier.weight(0.6f), fontWeight = FontWeight.Bold)
                }
                DividerLine()
            }
            items(sortedTeams.value, key = { it.value.id }) { row ->
                TeamRow(row.index + 1, row.value)
                DividerLine()
            }
        }
    }
}

@Composable
private fun TeamRow(place: Int, team: TeamEntity) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(tablePlaceColor(place), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 7.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(place.toString(), Modifier.weight(0.4f), style = MaterialTheme.typography.bodySmall)
        Row(Modifier.weight(1.8f)) {
            TeamBadge(team.name, Modifier.padding(end = 8.dp).size(32.dp))
            Text(team.name, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 9.dp))
        }
        Text(formatBalance(team.goalBalance), Modifier.weight(0.5f), style = MaterialTheme.typography.bodySmall)
        Text(team.points.toString(), Modifier.weight(0.6f), fontWeight = FontWeight.Bold)
    }
}

private fun tablePlaceColor(place: Int): Color {
    return when (place) {
        1 -> Color(0xFF1FA463).copy(alpha = 0.16f)
        in 2..5 -> Color(0xFF0B2D5B).copy(alpha = 0.16f)
        in 6..7 -> Color(0xFFF28C28).copy(alpha = 0.18f)
        8 -> Color(0xFF67C7F7).copy(alpha = 0.20f)
        in 18..20 -> Color(0xFFE53935).copy(alpha = 0.16f)
        else -> Color.Transparent
    }
}

private fun formatBalance(balance: Int): String {
    return if (balance > 0) "+$balance" else balance.toString()
}
