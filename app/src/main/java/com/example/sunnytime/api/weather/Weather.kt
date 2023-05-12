package com.example.sunnytime.api.weathermodel

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val generationtime_ms: Double,
    val utc_offset_seconds: Int,
    val timezone: String,
    val timezone_abbreviation: String,
    val elevation: Double,
    val daily_units: DailyUnits,
    val daily: Daily
)

@Serializable
data class DailyUnits(
    val time: String,
    val uv_index_max: String
)

@Serializable
data class Daily(
    val time: List<String>,
    val uv_index_max: List<Double>
)



