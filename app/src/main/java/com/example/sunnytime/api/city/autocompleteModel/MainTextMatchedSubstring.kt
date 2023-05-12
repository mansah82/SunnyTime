package com.example.sunnytime.api.city.autocompleteModel

import kotlinx.serialization.Serializable

@Serializable
data class MainTextMatchedSubstring(
    val length: Int,
    val offset: Int
)