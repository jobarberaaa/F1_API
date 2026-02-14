package com.example.f1api.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.f1api.model.Race
import com.example.f1api.util.UiState
import com.example.f1api.viewmodel.RacesViewModel

@Composable
fun RacesScreen(viewModel: RacesViewModel = viewModel()) {

    val racesState by viewModel.racesState.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadRaces() }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        when (racesState) {

            is UiState.Loading ->
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            is UiState.Success -> {
                val races = (racesState as UiState.Success<List<Race>>).data

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(races) { race ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Season: ${race.season}", style = MaterialTheme.typography.titleMedium)
                                Text("Round: ${race.round}")
                                Text("Circuit: ${race.circuit ?: "Unknown"}")
                                Text("Date: ${race.date ?: "Unknown"}")
                                Text("Winner: ${race.winner ?: "Unknown"}")
                            }
                        }
                    }
                }
            }

            is UiState.Error ->
                Text(
                    text = (racesState as UiState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
        }
    }
}
