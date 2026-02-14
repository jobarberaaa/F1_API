package com.example.f1api.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1api.model.Team
import com.example.f1api.repository.F1Repository
import com.example.f1api.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TeamsViewModel(private val repo: F1Repository) : ViewModel() {
    private val _teamsState = MutableStateFlow<UiState<List<Team>>>(UiState.Loading)
    val teamsState: StateFlow<UiState<List<Team>>> = _teamsState

    fun loadTeams() {
        viewModelScope.launch {
            try {
                val response = repo.getTeams()
                if (response.isSuccessful) {
                    _teamsState.value = UiState.Success(response.body()?.teams ?: emptyList())
                } else {
                    _teamsState.value = UiState.Error("Error ${response.code()}")
                }
            } catch (e: Exception) {
                _teamsState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}
