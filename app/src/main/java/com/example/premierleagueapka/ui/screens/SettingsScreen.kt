package com.example.premierleagueapka.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.premierleagueapka.data.local.UserPreferences
import com.example.premierleagueapka.ui.components.AppScreen

@Composable
fun SettingsScreen(navController: NavController) {
    val context = LocalContext.current
    val preferences = remember { UserPreferences(context) }

    var name by remember { mutableStateOf(preferences.getName()) }
    var favoriteClub by remember { mutableStateOf(preferences.getFavoriteClub()) }
    var birthDate by remember { mutableStateOf(preferences.getBirthDate()) }
    var savedMessage by remember { mutableStateOf("") }
    var darkMode by remember { mutableStateOf(false) }
    var notifications by remember { mutableStateOf(true) }

    AppScreen("Ustawienia", navController) {
        Column(Modifier.fillMaxSize().padding(top = 16.dp)) {
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    savedMessage = ""
                },
                label = { Text("Imię") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
            )
            OutlinedTextField(
                value = favoriteClub,
                onValueChange = {
                    favoriteClub = it
                    savedMessage = ""
                },
                label = { Text("Ulubiony klub") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
            )
            OutlinedTextField(
                value = birthDate,
                onValueChange = {
                    birthDate = it
                    savedMessage = ""
                },
                label = { Text("Data urodzenia") },
                placeholder = { Text("DD.MM.YYYY") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
            )
            Button(
                onClick = {
                    preferences.save(name.trim(), favoriteClub.trim(), birthDate.trim())
                    savedMessage = "Zapisano"
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Zapisz")
            }
            if (savedMessage.isNotEmpty()) {
                Text(savedMessage, modifier = Modifier.padding(bottom = 8.dp))
            }
            ToggleRow("Dark Mode", darkMode) { darkMode = it }
            ToggleRow("Powiadomienia", notifications) { notifications = it }
        }
    }
}

@Composable
private fun ToggleRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
