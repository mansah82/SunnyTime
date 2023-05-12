package com.example.sunnytime.api.city

import com.example.sunnytime.api.city.placeIdModel.PlaceIdResponse
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*

interface PlaceIdService {

    suspend fun getCoordinates(placeId: String): PlaceIdResponse?

    companion object {
        fun create(): PlaceIdService {

            return PlaceIdServiceImpl(
                client = HttpClient(Android) {
                    install(JsonFeature) {
                        serializer = KotlinxSerializer()
                    }
                }
            )
        }
    }

}