package com.example.premierleagueapka.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.premierleagueapka.R
import com.example.premierleagueapka.data.local.database.DatabaseProvider
import com.example.premierleagueapka.data.local.entity.NewsEntity
import com.example.premierleagueapka.ui.components.AppCardBorder
import com.example.premierleagueapka.ui.components.AppScreen

@Composable
fun NewsDetailScreen(navController: NavController, newsId: Int) {
    val context = LocalContext.current
    val db = DatabaseProvider.getDatabase(context)
    var news by remember { mutableStateOf<NewsEntity?>(null) }

    LaunchedEffect(newsId) {
        news = db.newsDao().getById(newsId)
    }

    AppScreen("Wiadomość", navController) {
        news?.let { item ->
            Column(Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(newsImage(context, item.imageName)),
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Spacer(Modifier.height(12.dp))
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, AppCardBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(14.dp)) {
                        Text(
                            item.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(10.dp))
                        Text(
                            item.content,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        } ?: Text("Nie znaleziono wiadomości")
    }
}

private fun newsImage(context: android.content.Context, imageName: String): Int {
    val imageId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
    return if (imageId != 0) imageId else R.drawable.pl_lion
}
