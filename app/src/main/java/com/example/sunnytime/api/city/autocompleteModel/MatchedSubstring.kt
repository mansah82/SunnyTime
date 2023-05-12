package com.example.sunnytime.api.city.autocompleteModel

import kotlinx.serialization.Serializable

@Serializable
data class MatchedSubstring(
    val length: Int,
    val offset: Int
)