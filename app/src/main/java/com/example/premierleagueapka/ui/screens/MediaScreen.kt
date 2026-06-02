package com.example.premierleagueapka.ui.screens

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.premierleagueapka.R
import com.example.premierleagueapka.ui.components.AppScreen

private const val YOUTUBE_URL = "https://www.youtube.com/watch?v=lY6KWl1llGw"

@Composable
fun MediaScreen(navController: NavController) {
    val context = LocalContext.current
    val anthemResId = remember {
        val resourceId = context.resources.getIdentifier(
            "tottenham_anthem",
            "raw",
            context.packageName
        )
        if (resourceId != 0) resourceId else R.raw.match_whistle
    }
    val audio = remember(anthemResId) { MediaPlayer.create(context, anthemResId) }

    DisposableEffect(Unit) {
        onDispose {
            audio.release()
        }
    }

    AppScreen("Media", navController) {
        Column(Modifier.fillMaxSize()) {
            Text("Skrót ostatniej kolejki", modifier = Modifier.padding(bottom = 8.dp))
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_URL))
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            ) {
                Text("Włącz skrót ostatniej kolejki")
            }
            Button(
                onClick = {
                    if (audio.isPlaying) audio.pause() else audio.start()
                },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Odtwórz hymn Tottenhamu")
            }
        }
    }
}
