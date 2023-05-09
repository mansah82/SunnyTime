package com.example.sunnytime.viewmodel.model

import com.example.sunnytime.api.weathermodel.WeatherResponse

data class State(
    val weather: WeatherResponse? = null,
    val isWeatherShowing: Boolean = false,
    val uvIndex: Double? = 0.0
)
