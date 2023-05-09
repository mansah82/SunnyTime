package com.example.sunnytime.api

import com.example.sunnytime.api.weathermodel.WeatherResponse
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*

interface WeatherService {

    suspend fun getWeather(
        lng: Double,
        lat: Double,
        dayToFetch: String
    ): WeatherResponse?

    companion object {
        fun create(): WeatherService {
            return WeatherServiceImpl(
                client = HttpClient(Android) {
                    install(JsonFeature) {
                        serializer = KotlinxSerializer()
                    }
                }
            )
        }
    }
}