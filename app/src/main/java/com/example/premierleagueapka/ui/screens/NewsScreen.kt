package com.example.premierleagueapka.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.premierleagueapka.data.local.database.DatabaseProvider
import com.example.premierleagueapka.ui.components.AppScreen
import com.example.premierleagueapka.viewmodel.NewsViewModel

@Composable
fun NewsScreen(navController: NavController) {

    val context = LocalContext.current
    val db = DatabaseProvider.getDatabase(context)
    val viewModel = remember { NewsViewModel(db) }

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    AppScreen("Wiadomości", navController) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(viewModel.news) { news ->
                Surface(
                    tonalElevation = 1.dp,
                    shadowElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        news.title,
                        style = androidx.compose.material3.MaterialTheme.typography.titleLarge
                    )
                    Text(
                        news.content,
                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
                    )
                    }
                }
            }
        }
    }
}
