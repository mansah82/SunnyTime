package com.example.sunnytime.api.city

import com.example.sunnytime.api.city.autocompleteModel.CitiesResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class AutocompleteServiceImpl(
    private val client: HttpClient,
) : AutocompleteService {


    override suspend fun getCities(searchText: String): CitiesResponse? {
        return try {
            val response = client.get<HttpResponse> {
                url(
                    "https://maps.googleapis.com/maps/" +
                            "api/place/autocomplete/json?" +
                            "input=${searchText}&" +
                            "language=EN&" +
                            "key="

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
