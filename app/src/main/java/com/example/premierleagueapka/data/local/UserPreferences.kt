package com.example.premierleagueapka.data.local

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {
    private val prefs = context.applicationContext.getSharedPreferences("user_settings", Context.MODE_PRIVATE)

    fun getName(): String = prefs.getString(KEY_NAME, DEFAULT_NAME) ?: DEFAULT_NAME

    fun getFavoriteClub(): String = prefs.getString(KEY_CLUB, DEFAULT_CLUB) ?: DEFAULT_CLUB

    fun getBirthDate(): String = prefs.getString(KEY_BIRTH_DATE, "") ?: ""

    fun isAnthemEnabled(): Boolean = prefs.getBoolean(KEY_ANTHEM_ENABLED, true)

    fun setAnthemEnabled(enabled: Boolean) {
        prefs.edit()
            .putBoolean(KEY_ANTHEM_ENABLED, enabled)
            .apply()
    }

    fun registerListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        prefs.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        prefs.unregisterOnSharedPreferenceChangeListener(listener)
    }

    fun save(name: String, favoriteClub: String, birthDate: String) {
        prefs.edit()
            .putString(KEY_NAME, name)
            .putString(KEY_CLUB, favoriteClub)
            .putString(KEY_BIRTH_DATE, birthDate)
            .apply()
    }

    companion object {
        private const val KEY_NAME = "name"
        private const val KEY_CLUB = "favorite_club"
        private const val KEY_BIRTH_DATE = "birth_date"
        const val KEY_ANTHEM_ENABLED = "anthem_enabled"
        private const val DEFAULT_NAME = "Paweł"
        private const val DEFAULT_CLUB = "Tottenham"
    }
}
