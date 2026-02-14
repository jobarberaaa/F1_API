package com.example.f1api.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1api.model.Season
import com.example.f1api.repository.F1Repository
import com.example.f1api.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SeasonsViewModel(private val repo: F1Repository) : ViewModel() {
    private val _seasonsState = MutableStateFlow<UiState<List<Season>>>(UiState.Loading)
    val seasonsState: StateFlow<UiState<List<Season>>> = _seasonsState

    fun loadSeasons() {
        viewModelScope.launch {
            try {
                val response = repo.getSeasons()
                if (response.isSuccessful) {
                    _seasonsState.value = UiState.Success(response.body()?.championships ?: emptyList())
                } else {
                    _seasonsState.value = UiState.Error("Error ${response.code()}")
                }
            } catch (e: Exception) {
                _seasonsState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}

