package com.example.premierleagueapka.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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

@Composable
fun AppScreen(
    title: String,
    navController: NavController,
    showBack: Boolean = true,
    content: @Composable () -> Unit
) {
    Surface(color = Color.White) {
        Column(Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .displayCutoutPadding()
                    .height(56.dp)
                    .padding(horizontal = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showBack) {
                    Surface(
                        modifier = Modifier
                            .size(46.dp)
                            .clickable { navController.popBackStack() },
                        color = Color(0xFFF2F2F2),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("<", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                } else {
                    Spacer(Modifier.width(46.dp))
                }
                Text(
                    text = title,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
                Spacer(Modifier.width(46.dp))
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
    Column {
        DividerLine()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(58.dp)
                .background(Color.White)
                .padding(horizontal = 4.dp, vertical = 4.dp),
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
            .padding(horizontal = 1.dp, vertical = 10.dp),
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun TeamBadge(team: String, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(teamBadge(team)),
        contentDescription = team,
        contentScale = ContentScale.Fit,
        modifier = modifier.size(38.dp)
    )
}

@Composable
fun AnimatedPremierLogo(modifier: Modifier = Modifier) {
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
        painter = painterResource(R.drawable.pl_lion),
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

val PremierPurpleColor: Color = PremierPurple
