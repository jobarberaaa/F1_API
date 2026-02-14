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
import com.example.f1api.model.Driver
import com.example.f1api.util.UiState
import com.example.f1api.viewmodel.DriversViewModel

@Composable
fun DriversScreen(viewModel: DriversViewModel = viewModel()) {
    val driversState by viewModel.driversState.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadDrivers() }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        when (driversState) {
            is UiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is UiState.Success -> LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items((driversState as UiState.Success<List<Driver>>).data) { driver ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "${driver.name} ${driver.surname}", style = MaterialTheme.typography.titleMedium)
                            Text(text = driver.nationality ?: "Unknown", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
            is UiState.Error -> Text(
                text = (driversState as UiState.Error).message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
