package com.example.odev_2.presentation

sealed class Screen(val route: String) {
    object MainScreen: Screen("main_screen")
    object AddingScreen: Screen("adding_screen")
}
