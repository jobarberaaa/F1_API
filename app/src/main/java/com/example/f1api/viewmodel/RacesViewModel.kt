package com.example.f1api.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1api.model.Race
import com.example.f1api.repository.F1Repository
import com.example.f1api.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RacesViewModel(private val repo: F1Repository) : ViewModel() {
    private val _racesState = MutableStateFlow<UiState<List<Race>>>(UiState.Loading)
    val racesState: StateFlow<UiState<List<Race>>> = _racesState

    fun loadRaces(year: Int = 2024) {
        viewModelScope.launch {
            try {
                val response = repo.getRacesByYear(year)
                if (response.isSuccessful) {
                    _racesState.value = UiState.Success(response.body()?.races ?: emptyList())
                } else {
                    _racesState.value = UiState.Error("Error ${response.code()}")
                }
            } catch (e: Exception) {
                _racesState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}
