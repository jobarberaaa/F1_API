package com.example.f1api.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1api.model.Season
import com.example.f1api.repository.F1Repository
import com.example.f1api.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SeasonsViewModel(private val repository: F1Repository = F1Repository()) : ViewModel() {

    private val _seasonsState = MutableStateFlow<UiState<List<Season>>>(UiState.Loading)
    val seasonsState: StateFlow<UiState<List<Season>>> = _seasonsState

    fun loadSeasons() {
        viewModelScope.launch {
            _seasonsState.value = UiState.Loading
            try {
                val response = repository.getSeasons()
                if (response.isSuccessful) {
                    val seasons = response.body()?.seasons ?: emptyList()
                    _seasonsState.value = UiState.Success(seasons)
                } else {
                    _seasonsState.value = UiState.Error("Error ${response.code()}: ${response.message()}")
                }
            } catch (e: Exception) {
                _seasonsState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}
