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
import com.example.f1api.model.Standing
import com.example.f1api.util.UiState
import com.example.f1api.viewmodel.StandingsViewModel

@Composable
fun StandingsScreen(viewModel: StandingsViewModel = viewModel()) {

    val standingsState by viewModel.standingsState.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadStandings() }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        when (standingsState) {

            is UiState.Loading ->
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            is UiState.Success -> {
                val standings = (standingsState as UiState.Success<List<Standing>>).data

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(standings) { standing ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("${standing.position}. ${standing.driver ?: "Unknown"}",
                                    style = MaterialTheme.typography.titleMedium)
                                Text("Team: ${standing.team ?: "Unknown"}")
                                Text("Points: ${standing.points ?: 0f}")
                            }
                        }
                    }
                }
            }

            is UiState.Error ->
                Text(
                    text = (standingsState as UiState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
        }
    }
}
