package com.example.odev_2.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.odev_2.KategoriRepository
import com.example.odev_2.Parca
import com.example.odev_2.ParcaRepository

@Composable
fun Navigation(
    kategoriRepository: KategoriRepository,
    parcaRepository: ParcaRepository,
    kategorikParcalar: MutableList<Parca>
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(
            route = Screen.MainScreen.route
        ) {
            MainScreen(navController, kategoriRepository, parcaRepository, kategorikParcalar)
        }
        composable(
            route = Screen.AddingScreen.route
        ) {
            AddingScreen(navController, kategoriRepository, parcaRepository)
        }
    }
}