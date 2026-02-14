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
import com.example.f1api.model.Team
import com.example.f1api.util.UiState
import com.example.f1api.viewmodel.TeamsViewModel

@Composable
fun TeamsScreen(viewModel: TeamsViewModel = viewModel()) {

    val teamsState by viewModel.teamsState.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadTeams() }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        when (teamsState) {

            is UiState.Loading ->
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            is UiState.Success -> {
                val teams = (teamsState as UiState.Success<List<Team>>).data

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(teams) { team ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(team.name, style = MaterialTheme.typography.titleMedium)
                                Text("Nationality: ${team.nationality ?: "Unknown"}")
                                Text("URL: ${team.url ?: "Unknown"}")
                            }
                        }
                    }
                }
            }

            is UiState.Error ->
                Text(
                    text = (teamsState as UiState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
        }
    }
}
