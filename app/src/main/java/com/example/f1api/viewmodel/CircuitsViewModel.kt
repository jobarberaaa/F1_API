package com.example.f1api.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1api.model.Circuit
import com.example.f1api.repository.F1Repository
import com.example.f1api.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CircuitsViewModel(private val repository: F1Repository = F1Repository()) : ViewModel() {

    private val _circuitsState = MutableStateFlow<UiState<List<Circuit>>>(UiState.Loading)
    val circuitsState: StateFlow<UiState<List<Circuit>>> = _circuitsState

    fun loadCircuits() {
        viewModelScope.launch {
            _circuitsState.value = UiState.Loading
            try {
                val response = repository.getCircuits()
                if (response.isSuccessful) {
                    val circuits = response.body()?.circuits ?: emptyList()
                    _circuitsState.value = UiState.Success(circuits)
                } else {
                    _circuitsState.value = UiState.Error("Error ${response.code()}: ${response.message()}")
                }
            } catch (e: Exception) {
                _circuitsState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}
