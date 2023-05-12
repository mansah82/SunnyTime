package com.example.sunnytime.api.city.placeIdModel

import kotlinx.serialization.Serializable

@Serializable
data class Result(
    val address_components: List<AddressComponent>,
    val formatted_address: String,
    val geometry: Geometry,
    val place_id: String,
    val types: List<String>
)