package com.example.sunnytime.api.city.placeIdModel

import kotlinx.serialization.Serializable

@Serializable
data class Bounds(
    val northeast: Northeast,
    val southwest: Southwest
)