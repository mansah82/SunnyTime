package com.example.sunnytime.api.city.placeIdModel

import kotlinx.serialization.Serializable

@Serializable
data class Viewport(
    val northeast: Northeast,
    val southwest: Southwest
)