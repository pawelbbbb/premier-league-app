package com.example.premierleagueapka

import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.premierleagueapka.data.local.UserPreferences
import com.example.premierleagueapka.navigation.NavGraph
import com.example.premierleagueapka.ui.theme.PremierLeagueApkaTheme


class MainActivity : ComponentActivity() {
    private var backgroundAudio: MediaPlayer? = null
    private lateinit var preferences: UserPreferences
    private val preferenceListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key == UserPreferences.KEY_ANTHEM_ENABLED) {
            updateAnthemPlayback()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = UserPreferences(this)
        preferences.registerListener(preferenceListener)
        updateAnthemPlayback()

        setContent {
            PremierLeagueApkaTheme(dynamicColor = false) {
                val navController = rememberNavController()

                NavGraph(navController)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateAnthemPlayback()
    }

    private fun updateAnthemPlayback() {
        if (preferences.isAnthemEnabled()) {
            if (backgroundAudio == null) {
                backgroundAudio = MediaPlayer.create(this, R.raw.pl_anthem)?.apply {
                    isLooping = true
                    setVolume(0.35f, 0.35f)
                    start()
                }
            } else if (backgroundAudio?.isPlaying == false) {
                backgroundAudio?.start()
            }
        } else {
            backgroundAudio?.release()
            backgroundAudio = null
        }
    }

    fun setAnthemPlaybackEnabled(enabled: Boolean) {
        preferences.setAnthemEnabled(enabled)
        updateAnthemPlayback()
    }

    override fun onDestroy() {
        preferences.unregisterListener(preferenceListener)
        backgroundAudio?.release()
        backgroundAudio = null
        super.onDestroy()
    }
}
