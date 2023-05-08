package com.example.sunnytime.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sunnytime.model.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state

    fun updateWeatherShowing(isShowing: Boolean) {
        val currentState = _state.value
        _state.value = currentState.copy(isWeatherShowing = isShowing)
    }
}

