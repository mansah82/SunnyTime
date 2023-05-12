package com.example.sunnytime.api.city.placeIdModel

import kotlinx.serialization.Serializable


@Serializable
data class Geometry(
    val bounds: Bounds,
    val location: Location,
    val location_type: String,
    val viewport: Viewport
)