package com.example.sunnytime.api.weather

import com.example.sunnytime.api.weathermodel.WeatherResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class WeatherServiceImpl(
    private val client: HttpClient
) : WeatherService {

    override suspend fun getWeather(
        lng: Double,
        lat: Double,
        dayToFetch: String
    ): WeatherResponse? {
        return try {
            val response = client.get<HttpResponse> {
                url(
                    "https://api.open-meteo.com/v1/forecast?&" +
                            "latitude=${lat}&longitude=${lng}&" +
                            "timezone=Europe%2FBerlin&" +
                            "daily=uv_index_max&" +
                            "start_date=${dayToFetch}&end_date=${dayToFetch}"
                )
            }
            if (response.status.isSuccess()) {
                val json = response.receive<String>()
                Json.decodeFromString(json)

            } else {
                null
            }
        } catch (e: RedirectResponseException) {
            println("Error ${e.response.status.description}")
            null
        }
    }
}