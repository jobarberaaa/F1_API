package com.example.f1api.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1api.model.Standing
import com.example.f1api.repository.F1Repository
import com.example.f1api.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StandingsViewModel(private val repo: F1Repository) : ViewModel() {
    private val _standingsState = MutableStateFlow<UiState<List<Standing>>>(UiState.Loading)
    val standingsState: StateFlow<UiState<List<Standing>>> = _standingsState

    fun loadDriversChampionship(year: Int) {
        viewModelScope.launch {
            try {
                val response = repo.getDriversChampionship(year)
                if (response.isSuccessful) {
                    _standingsState.value = UiState.Success(response.body()?.drivers_championship ?: emptyList())
                } else {
                    _standingsState.value = UiState.Error("Error ${response.code()}")
                }
            } catch (e: Exception) {
                _standingsState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}
