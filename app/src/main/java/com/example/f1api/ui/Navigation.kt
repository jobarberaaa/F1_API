package com.example.f1api.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.f1api.ui.*

object Routes {
    const val DRIVERS = "drivers"
    const val TEAMS = "teams"
    const val CIRCUITS = "circuits"
    const val SEASONS = "seasons"
    const val RESULTS = "results"
    const val STANDINGS = "standings"
}

@Composable
fun F1NavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.DRIVERS) {
        composable(Routes.DRIVERS) { DriversScreen() }
        composable(Routes.TEAMS) { TeamsScreen() }
        composable(Routes.CIRCUITS) { CircuitsScreen() }
        composable(Routes.SEASONS) { SeasonsScreen() }
        composable(Routes.RESULTS) { ResultsScreen(year = 2024, round = 1) } // Ejemplo round
        composable(Routes.STANDINGS) { StandingsScreen(year = 2024) }       // Ejemplo año
    }
}
