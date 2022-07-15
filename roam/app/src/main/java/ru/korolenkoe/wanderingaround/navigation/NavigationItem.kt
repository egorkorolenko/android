package ru.korolenkoe.wanderingaround.navigation

import ru.korolenkoe.wanderingaround.R

sealed class NavigationItem(var route: String, var icon: Int, var title:String){
    object Home: NavigationItem("home", R.drawable.home,"Главная")
    object Map: NavigationItem("map", R.drawable.map,"Карта")
    object Profile: NavigationItem("profile", R.drawable.person,"Профиль")
}
