package com.example.f1api.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1api.model.Driver
import com.example.f1api.repository.F1Repository
import com.example.f1api.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DriversViewModel(private val repository: F1Repository = F1Repository()) : ViewModel() {

    private val _driversState = MutableStateFlow<UiState<List<Driver>>>(UiState.Loading)
    val driversState: StateFlow<UiState<List<Driver>>> = _driversState

    fun loadDrivers() {
        viewModelScope.launch {
            _driversState.value = UiState.Loading
            try {
                val response = repository.getDrivers()
                if (response.isSuccessful) {
                    val drivers = response.body()?.drivers ?: emptyList()
                    _driversState.value = UiState.Success(drivers)
                } else {
                    _driversState.value = UiState.Error("Error ${response.code()}: ${response.message()}")
                }
            } catch (e: Exception) {
                _driversState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}
