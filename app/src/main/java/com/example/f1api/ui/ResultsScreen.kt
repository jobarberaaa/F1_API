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
import com.example.f1api.model.Result
import com.example.f1api.util.UiState
import com.example.f1api.viewmodel.ResultsViewModel

@Composable
fun ResultsScreen(year: Int, round: Int, viewModel: ResultsViewModel = viewModel()) {
    val resultsState by viewModel.resultsState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadResults(year, round)
    }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        when (resultsState) {
            is UiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is UiState.Success -> LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items((resultsState as UiState.Success<List<Result>>).data) { result ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = result.winner ?: "Unknown",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Team: ${result.team ?: "Unknown"}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Laps: ${result.laps ?: "-"}  Time: ${result.time ?: "-"}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
            is UiState.Error -> Text(
                text = (resultsState as UiState.Error).message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
