package com.example.premierleagueapka.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.premierleagueapka.R

private val PremierPurple = Color(0xFF37003C)
private val LightLine = Color(0xFFE3E3E3)
private val AppBackground = Color(0xFFF8F9FB)
private val CardBorder = Color(0xFFE7EAF0)

@Composable
fun AppScreen(
    title: String,
    navController: NavController,
    showBack: Boolean = true,
    content: @Composable () -> Unit
) {
    Surface(color = AppBackground) {
        Column(Modifier.fillMaxSize()) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .displayCutoutPadding()
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                color = PremierPurple,
                shape = RoundedCornerShape(10.dp),
                shadowElevation = 0.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(horizontal = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (showBack) {
                        Surface(
                            modifier = Modifier
                            .size(42.dp)
                            .clickable { navController.popBackStack() },
                            color = Color.White.copy(alpha = 0.14f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("<", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }
                    } else {
                        Spacer(Modifier.width(42.dp))
                    }
                    Text(
                        text = title,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                    Spacer(Modifier.width(42.dp))
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                content()
            }
            BottomBar(navController)
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppBackground)
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(56.dp)
                .background(Color.White)
                .border(1.dp, CardBorder, RoundedCornerShape(12.dp))
                .padding(horizontal = 4.dp, vertical = 3.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItem("Tabela") { navController.navigate("table") }
            NavItem("Mecze") { navController.navigate("matches") }
            NavItem("Bramki") { navController.navigate("statistics") }
            NavItem("News") { navController.navigate("news") }
            NavItem("Media") { navController.navigate("media") }
            NavItem("Opcje") { navController.navigate("settings") }
        }
    }
}

@Composable
private fun RowScope.NavItem(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        modifier = Modifier
            .weight(1f)
            .clickable(onClick = onClick)
            .padding(horizontal = 1.dp, vertical = 9.dp),
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun TeamBadge(team: String, modifier: Modifier = Modifier.size(38.dp)) {
    val context = LocalContext.current
    Image(
        painter = painterResource(safeDrawable(context, teamBadge(team))),
        contentDescription = team,
        contentScale = ContentScale.Fit,
        modifier = modifier
    )
}

@Composable
fun AnimatedPremierLogo(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val transition = rememberInfiniteTransition(label = "logo")
    val alpha by transition.animateFloat(
        initialValue = 0.55f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(900),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Image(
        painter = painterResource(safeDrawable(context, R.drawable.pl_lion)),
        contentDescription = "Premier League",
        modifier = modifier.alpha(alpha)
    )
}

@Composable
fun DividerLine() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(LightLine)
    )
}

fun teamBadge(team: String): Int {
    val key = team.lowercase()
    return when {
        "arsenal" in key -> R.drawable.arsenal
        "aston villa" in key -> R.drawable.villa
        "bournemouth" in key -> R.drawable.bournemouth
        "brentford" in key -> R.drawable.brendford
        "brighton" in key -> R.drawable.brighton
        "burnley" in key -> R.drawable.burnley
        "chelsea" in key -> R.drawable.chelsea
        "crystal palace" in key -> R.drawable.palace
        "everton" in key -> R.drawable.everton
        "fulham" in key -> R.drawable.fulham
        "leeds" in key -> R.drawable.leeds
        "liverpool" in key -> R.drawable.liverpool
        "manchester city" in key -> R.drawable.mancity
        "manchester united" in key -> R.drawable.united
        "newcastle" in key -> R.drawable.newcastle
        "sunderland" in key -> R.drawable.sunderland
        "tottenham" in key -> R.drawable.tottenham
        "west ham" in key -> R.drawable.west_ham
        "wolves" in key || "wolverhampton" in key -> R.drawable.wolves
        "nottingham forest" in key -> R.drawable.forest
        else -> R.drawable.pl_lion
    }
}

private fun safeDrawable(context: android.content.Context, drawableId: Int): Int {
    val hasBytes = runCatching {
        context.resources.openRawResource(drawableId).use { stream ->
            stream.read() != -1
        }
    }.getOrDefault(false)

    return if (hasBytes) drawableId else R.drawable.guardiola
}

val PremierPurpleColor: Color = PremierPurple
val AppCardBorder: Color = CardBorder
val AppBackgroundColor: Color = AppBackground
