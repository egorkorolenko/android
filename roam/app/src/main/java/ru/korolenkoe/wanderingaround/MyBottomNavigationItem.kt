package ru.korolenkoe.wanderingaround

sealed class MyBottomNavigationItem(var route: String, var icon: Int, var title:String){
    object Home: MyBottomNavigationItem("home",R.drawable.home,"Главная")
    object Map: MyBottomNavigationItem("map",R.drawable.map,"Карта")
    object Profile: MyBottomNavigationItem("profile",R.drawable.person,"Профиль")
}
