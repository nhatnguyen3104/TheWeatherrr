package com.example.theweatherrr.presentation.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theweatherrr.presentation.di.RepositoryModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val appRepository = RepositoryModule.appRepository

    private var _uiState: MutableStateFlow<MainUiState?> = MutableStateFlow(null)
    val uiState: StateFlow<MainUiState?> = _uiState.asStateFlow()

    fun fetchWeather() {
        viewModelScope.launch {
            appRepository.getCurrentWeather().collect { weather ->
                _uiState.update { currentState ->
                    currentState?.copy(
                        weather = weather
                    )
                }

            }
        }
    }
}