package com.example.sunnytime.api.city.placeIdModel

import kotlinx.serialization.Serializable


@Serializable
data class PlaceIdResponse(
    val results: List<Result>,
    val status: String
)