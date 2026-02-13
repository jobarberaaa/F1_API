package com.example.f1api.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1api.model.Team
import com.example.f1api.repository.F1Repository
import com.example.f1api.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TeamsViewModel(private val repository: F1Repository = F1Repository()) : ViewModel() {

    private val _teamsState = MutableStateFlow<UiState<List<Team>>>(UiState.Loading)
    val teamsState: StateFlow<UiState<List<Team>>> = _teamsState

    fun loadTeams() {
        viewModelScope.launch {
            _teamsState.value = UiState.Loading
            try {
                val response = repository.getTeams()
                if (response.isSuccessful) {
                    val teams = response.body()?.teams ?: emptyList()
                    _teamsState.value = UiState.Success(teams)
                } else {
                    _teamsState.value = UiState.Error("Error ${response.code()}: ${response.message()}")
                }
            } catch (e: Exception) {
                _teamsState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}
