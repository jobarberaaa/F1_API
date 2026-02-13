package com.example.f1api.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1api.model.Standing
import com.example.f1api.repository.F1Repository
import com.example.f1api.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StandingsViewModel(private val repository: F1Repository = F1Repository()) : ViewModel() {

    private val _standingsState = MutableStateFlow<UiState<List<Standing>>>(UiState.Loading)
    val standingsState: StateFlow<UiState<List<Standing>>> = _standingsState

    fun loadStandings() {
        viewModelScope.launch {
            _standingsState.value = UiState.Loading
            try {
                val response = repository.getStandings()
                if (response.isSuccessful) {
                    val standings = response.body()?.standings ?: emptyList()
                    _standingsState.value = UiState.Success(standings)
                } else {
                    _standingsState.value = UiState.Error("Error ${response.code()}: ${response.message()}")
                }
            } catch (e: Exception) {
                _standingsState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}
