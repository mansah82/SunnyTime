package com.example.sunnytime.api.city.autocompleteModel

import kotlinx.serialization.Serializable


@Serializable
data class CitiesResponse(
    val predictions: List<Prediction>,
    val status: String
)