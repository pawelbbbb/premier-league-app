package com.example.premierleagueapka.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.example.premierleagueapka.ui.components.AppCardBorder
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
        if (viewModel.news.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Brak wiadomości")
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(viewModel.news, key = { it.id }) { news ->
                    Surface(
                        onClick = { navController.navigate("news_detail/${news.id}") },
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, AppCardBorder),
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
}
