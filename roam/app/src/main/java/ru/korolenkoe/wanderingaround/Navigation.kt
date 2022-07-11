package ru.korolenkoe.wanderingaround

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun Navigation() {
    val navHostController = rememberNavController()
    NavHost(navController = navHostController, startDestination = "splash_screen") {
        composable(route = "splash_screen") {
            SplashScreen(navController = navHostController)
        }
        composable(route = "main_screen") {
            MainScreen()
        }
    }
}
