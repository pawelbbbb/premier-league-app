package com.example.premierleagueapka.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.premierleagueapka.data.local.UserPreferences
import com.example.premierleagueapka.ui.components.AnimatedPremierLogo
import com.example.premierleagueapka.ui.components.AppCardBorder
import com.example.premierleagueapka.ui.components.AppScreen
import com.example.premierleagueapka.ui.components.PremierPurpleColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text

@Composable
fun MenuScreen(navController: NavController) {
    val context = LocalContext.current
    val preferences = remember { UserPreferences(context) }
    val name = preferences.getName()

    AppScreen("Wybór", navController, showBack = false) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedPremierLogo(Modifier.size(160.dp))
            Text("Cześć, $name!", modifier = Modifier.padding(bottom = 18.dp))

            MenuButton("Tabela") { navController.navigate("table") }
            MenuButton("Mecze i wyniki") { navController.navigate("matches") }
            MenuButton("Bramki") { navController.navigate("statistics") }
            MenuButton("Newsy") { navController.navigate("news") }
            MenuButton("Media") { navController.navigate("media") }
            MenuButton("Ustawienia") { navController.navigate("settings") }
        }
    }
}

@Composable
fun MenuButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = PremierPurpleColor),
        border = BorderStroke(1.dp, AppCardBorder),
        shape = RoundedCornerShape(10.dp),
        elevation = null
    ) {
        Text(text, fontSize = 14.sp)
    }
}
