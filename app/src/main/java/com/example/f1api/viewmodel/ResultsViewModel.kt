package com.example.f1api.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1api.model.Result
import com.example.f1api.repository.F1Repository
import com.example.f1api.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ResultsViewModel(private val repository: F1Repository = F1Repository()) : ViewModel() {

    private val _resultsState = MutableStateFlow<UiState<List<Result>>>(UiState.Loading)
    val resultsState: StateFlow<UiState<List<Result>>> = _resultsState

    fun loadResults(year: Int, round: Int) {
        viewModelScope.launch {
            _resultsState.value = UiState.Loading
            try {
                val response = repository.getFP1(year, round)
                if (response.isSuccessful) {
                    // Accedemos al Response real de la API
                    val results = response.body()?.races?.fp1Results ?: emptyList()
                    _resultsState.value = UiState.Success(results)
                } else {
                    _resultsState.value = UiState.Error("Error HTTP ${response.code()}")
                }
            } catch (e: Exception) {
                _resultsState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}
