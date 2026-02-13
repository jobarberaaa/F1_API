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

    fun loadResults() {
        viewModelScope.launch {
            _resultsState.value = UiState.Loading
            try {
                val response = repository.getResults()
                if (response.isSuccessful) {
                    val results = response.body()?.results ?: emptyList()
                    _resultsState.value = UiState.Success(results)
                } else {
                    _resultsState.value = UiState.Error("Error ${response.code()}: ${response.message()}")
                }
            } catch (e: Exception) {
                _resultsState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}
