package com.example.sunnytime.api.city

import com.example.sunnytime.api.city.autocompleteModel.CitiesResponse
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*

interface AutocompleteService {

    suspend fun getCities(searchText: String): CitiesResponse?

    companion object {
        fun create(): AutocompleteService {

            return AutocompleteServiceImpl(
                client = HttpClient(Android) {
                    install(JsonFeature) {
                        serializer = KotlinxSerializer()
                    }
                }
            )
        }
    }
}