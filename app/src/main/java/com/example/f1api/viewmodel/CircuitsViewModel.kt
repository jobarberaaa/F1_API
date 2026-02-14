package com.example.f1api.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1api.model.Circuit
import com.example.f1api.repository.F1Repository
import com.example.f1api.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CircuitsViewModel(private val repo: F1Repository) : ViewModel() {
    private val _circuitsState = MutableStateFlow<UiState<List<Circuit>>>(UiState.Loading)
    val circuitsState: StateFlow<UiState<List<Circuit>>> = _circuitsState

    fun loadCircuits() {
        viewModelScope.launch {
            try {
                val response = repo.getCircuits()
                if (response.isSuccessful) {
                    _circuitsState.value = UiState.Success(response.body()?.circuits ?: emptyList())
                } else {
                    _circuitsState.value = UiState.Error("Error ${response.code()}")
                }
            } catch (e: Exception) {
                _circuitsState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}
