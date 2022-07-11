package ru.korolenkoe.wanderingaround

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                Navigation()
            }
        }
    }
}

@Composable
fun MainScreen() {
    Scaffold(
        bottomBar = { BottomNavigationBar() }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            HiUserName(userName = "Egor")
            DateToday()
            WeatherNow()
            NumberSteps()
            Kilometers()
        }
    }
}

@Composable
fun HiUserName(userName: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 15.dp)
    ) {
        Text(text = "Добрый день, $userName", fontSize = 30.sp)
    }
}

@Composable
fun DateToday() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 60.dp)
    ) {
        Text(text = "Сегодня то-то бла-бла-бла", fontSize = 25.sp)
    }
}

@Composable
fun WeatherNow() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 100.dp)
    ) {
        Text(text = "Чичас на вулице ", fontSize = 25.sp)
        Text(text = "Наерно еще пикчу надо ", fontSize = 25.sp)
    }
}

@Composable
fun NumberSteps() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 170.dp)
    ) {
        Text(text = "Было сделано 5 шагов", fontSize = 25.sp)
    }
}

@Composable
fun Kilometers() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 210.dp)
    ) {
        Text(text = "Вы прошли 0 км", fontSize = 25.sp)
    }
}

@Composable
fun BottomNavigationBar() {
    val items = listOf(
        MyBottomNavigationItem.Home,
        MyBottomNavigationItem.Map,
        MyBottomNavigationItem.Profile
    )
    BottomNavigation(
        backgroundColor = Color.Gray,
        contentColor = Color.Black,

        ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = false,
                onClick = {
                    /* Add code later */
                })
        }
    }
}

@Composable
@Preview
fun ComposablePreview() {
    MainScreen()
//    val navHostController = rememberNavController()
//    SplashScreen(navController = navHostController)
}