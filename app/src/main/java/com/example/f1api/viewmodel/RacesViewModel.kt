package com.example.f1api.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1api.model.Race
import com.example.f1api.repository.F1Repository
import com.example.f1api.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RacesViewModel(private val repository: F1Repository = F1Repository()) : ViewModel() {

    private val _racesState = MutableStateFlow<UiState<List<Race>>>(UiState.Loading)
    val racesState: StateFlow<UiState<List<Race>>> = _racesState

    fun loadRaces() {
        viewModelScope.launch {
            _racesState.value = UiState.Loading
            try {
                val response = repository.getRaces()
                if (response.isSuccessful) {
                    val races = response.body()?.races ?: emptyList()
                    _racesState.value = UiState.Success(races)
                } else {
                    _racesState.value = UiState.Error("Error ${response.code()}: ${response.message()}")
                }
            } catch (e: Exception) {
                _racesState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}
