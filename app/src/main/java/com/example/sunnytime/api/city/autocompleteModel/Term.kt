package com.example.sunnytime.api.city.autocompleteModel

import kotlinx.serialization.Serializable

@Serializable
data class Term(
    val offset: Int,
    val value: String
)