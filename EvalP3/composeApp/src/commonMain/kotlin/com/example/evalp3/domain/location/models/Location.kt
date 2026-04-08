package com.example.evalp3.domain.location.models

/**
 * Business representation of a location in the Rick and Morty universe.
 * Used for list display.
 */
data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)
