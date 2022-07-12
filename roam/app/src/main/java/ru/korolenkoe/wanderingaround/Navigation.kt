package ru.korolenkoe.wanderingaround

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun Navigation(navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = "splash_screen") {
        composable(route = "splash_screen") {
            SplashScreen(navController = navHostController)
        }
        composable(BottomNavigationItem.Home.route){
            MainScreen()
        }
        composable(BottomNavigationItem.Map.route){
            MapScreen()
        }
        composable(BottomNavigationItem.Profile.route){
            ProfileScreen()
        }
    }
}
