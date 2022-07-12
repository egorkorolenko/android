package ru.korolenkoe.wanderingaround

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.korolenkoe.wanderingaround.ui.theme.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navHostController = rememberNavController()
    SplashScreen(navController = navHostController)
    Scaffold(
        bottomBar = { BottomNavigationBar(navHostController) }
    ) {
//        Navigation(navHostController)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(OrangeYellow1)
        ) {
            Column {
                HiUserName(userName = "Egor")
                DateToday()
                WeatherNow()
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    NumberSteps()
                    Kilometers()
                }
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    CurrentWalk()
                }
                StartWalk()
            }
        }
    }
}

@Composable
fun HiUserName(userName: String) {
    Column(
        modifier = Modifier
            .padding(10.dp, 15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(AquaBlue)
            .padding(15.dp, 5.dp)
            .fillMaxWidth()
    ) {
        Text(text = "Добрый день, $userName", fontSize = 30.sp)
    }
}

@Composable
fun DateToday() {
    Column(
        modifier = Modifier
            .padding(10.dp, 15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(AquaBlue)
            .padding(15.dp, 5.dp)
            .fillMaxWidth()
    ) {
        Text(text = "Сегодня то-то бла-бла-бла", fontSize = 25.sp)
    }
}

@Composable
fun WeatherNow() {
    Column(
        modifier = Modifier
            .padding(10.dp, 15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(AquaBlue)
            .padding(15.dp, 5.dp)
            .fillMaxWidth()
    ) {
        Text(text = "Чичас на вулице ", fontSize = 25.sp)
        Text(text = "Наерно еще пикчу надо ", fontSize = 25.sp)
    }
}

@Composable
fun NumberSteps() {
    Column(
        modifier = Modifier
            .size(190.dp, 120.dp)
            .padding(10.dp, 15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(AquaBlue)
            .padding(15.dp, 5.dp)
    ) {
        Text(text = "Было сделано 5 шагов", fontSize = 25.sp)
    }
}

@Composable
fun Kilometers() {
    Column(
        modifier = Modifier
            .size(190.dp, 120.dp)
            .padding(10.dp, 15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(AquaBlue)
            .padding(15.dp, 5.dp)
    ) {
        Text(text = "Вы прошли 0 км", fontSize = 25.sp)
    }
}

@Composable
fun CurrentWalk() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(LightGreen3)
            .padding(15.dp, 20.dp)
            .fillMaxWidth()
    ) {
        Column {
            Text(text = "Вы прошли 0 шагов", fontSize = 25.sp)
            Text(text = "Вы прошли 0 км", fontSize = 25.sp)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Map,
        BottomNavigationItem.Profile
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
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }
}

@Composable
fun StartWalk() {
    Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(15.dp, 15.dp)) {
        Text("Start", fontSize = 25.sp)
    }
}

@Composable
@Preview
fun ComposablePreview() {
    MainScreen()
//    val navHostController = rememberNavController()
//    SplashScreen(navController = navHostController)
}