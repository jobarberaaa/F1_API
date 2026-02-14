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
import com.example.f1api.model.Circuit
import com.example.f1api.util.UiState
import com.example.f1api.viewmodel.CircuitsViewModel

@Composable
fun CircuitsScreen(viewModel: CircuitsViewModel = viewModel()) {
    val circuitsState by viewModel.circuitsState.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadCircuits() }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        when (circuitsState) {
            is UiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is UiState.Success -> LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items((circuitsState as UiState.Success<List<Circuit>>).data) { circuit ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = circuit.name ?: "Unknown", style = MaterialTheme.typography.titleMedium)
                            Text(text = circuit.location ?: "Unknown", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
            is UiState.Error -> Text(
                text = (circuitsState as UiState.Error).message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
