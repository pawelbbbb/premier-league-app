package com.example.premierleagueapka.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.premierleagueapka.data.local.database.DatabaseProvider
import com.example.premierleagueapka.viewmodel.TableViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TableScreen(navController: NavController) {

    val context = LocalContext.current
    val db = DatabaseProvider.getDatabase(context)

    val viewModel = remember {
        TableViewModel(db)
    }

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tabela") },
                actions = {
                    Button(onClick = { navController.popBackStack() }) {
                        Text("Wróć")
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(modifier = Modifier.padding(padding)) {

            items(viewModel.teams) { team ->
                Text(
                    "${team.name} - ${team.points}",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}