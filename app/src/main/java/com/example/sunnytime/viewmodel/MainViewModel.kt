package com.example.sunnytime.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sunnytime.api.WeatherService
import com.example.sunnytime.viewmodel.model.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel() : ViewModel() {

    private val _state = MutableStateFlow(State())
    private val weatherService = WeatherService.create()
    val state: StateFlow<State> = _state
    private val currentState = _state.value


    fun getUVIndex() {
        viewModelScope.launch {
            try {
                val weatherResponse = withContext(Dispatchers.Unconfined) {
                    weatherService.getWeather(
                        lat = 22.33,
                        lng = 22.43,
                        dayToFetch = "2023-05-09"
                    )
                }
                _state.value = currentState.copy(
                    weather = weatherResponse,
                    uvIndex = weatherResponse?.daily?.uv_index_max?.get(0)
                )
            } catch (e: Exception) {
                println("Error ${e.message}")
            }
            println("UX ${state.value.uvIndex}")
        }
    }


    fun updateWeatherShowing(isShowing: Boolean) {

        _state.value = currentState.copy(isWeatherShowing = isShowing)
    }
}



