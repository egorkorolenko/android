package ru.korolenkoe.wanderingaround

sealed class BottomNavigationItem(var route: String, var icon: Int, var title:String){
    object Home: BottomNavigationItem("home",R.drawable.home,"Главная")
    object Map: BottomNavigationItem("map",R.drawable.map,"Карта")
    object Profile: BottomNavigationItem("profile",R.drawable.person,"Профиль")
}
