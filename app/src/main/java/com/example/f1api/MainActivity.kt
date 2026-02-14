package com.example.f1api.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*

import com.example.f1api.model.Driver
import com.example.f1api.model.Team
import com.example.f1api.model.Race
import com.example.f1api.util.UiState
import com.example.f1api.viewmodel.DriversViewModel
import com.example.f1api.viewmodel.TeamsViewModel
import com.example.f1api.viewmodel.RacesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            F1App()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun F1App() {
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopAppBar(title = { Text("F1 API") }) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        F1NavHost(navController, Modifier.padding(paddingValues))
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        Routes.DRIVERS,
        Routes.TEAMS,
        Routes.RACES
    )

    NavigationBar {
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination
        items.forEach { route ->
            val selected = currentDestination?.route == route
            NavigationBarItem(
                label = { Text(route.replaceFirstChar { it.uppercase() }) },
                selected = selected,
                onClick = {
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun F1NavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = Routes.DRIVERS, modifier = modifier) {
        composable(Routes.DRIVERS) { DriversScreen() }
        composable(Routes.TEAMS) { TeamsScreen() }
        composable(Routes.RACES) { RacesScreen() }
    }
}

object Routes {
    const val DRIVERS = "drivers"
    const val TEAMS = "teams"
    const val RACES = "races"
}

// ---------------- SCREENS SIMPLES ----------------

@Composable
fun DriversScreen(viewModel: DriversViewModel = viewModel()) {
    val state by viewModel.driversState.collectAsState()
    LaunchedEffect(Unit) { viewModel.loadDrivers() }

    Box(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        when (state) {
            is UiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is UiState.Success -> LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items((state as UiState.Success<List<Driver>>).data) { driver ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Text("${driver.name} ${driver.surname} - ${driver.nationality}", modifier = Modifier.padding(16.dp))
                    }
                }
            }
            is UiState.Error -> Text((state as UiState.Error).message, color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun TeamsScreen(viewModel: TeamsViewModel = viewModel()) {
    val state by viewModel.teamsState.collectAsState()
    LaunchedEffect(Unit) { viewModel.loadTeams() }

    Box(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        when (state) {
            is UiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is UiState.Success -> LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items((state as UiState.Success<List<Team>>).data) { team ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Text("${team.name} - ${team.nationality}", modifier = Modifier.padding(16.dp))
                    }
                }
            }
            is UiState.Error -> Text((state as UiState.Error).message, color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun RacesScreen(viewModel: RacesViewModel = viewModel()) {
    val state by viewModel.racesState.collectAsState()
    LaunchedEffect(Unit) { viewModel.loadRaces() }

    Box(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        when (state) {
            is UiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is UiState.Success -> LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items((state as UiState.Success<List<Race>>).data) { race ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Text("${race.circuit} - ${race.winner ?: "Pendiente"}", modifier = Modifier.padding(16.dp))
                    }
                }
            }
            is UiState.Error -> Text((state as UiState.Error).message, color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Center))
        }
    }
}
