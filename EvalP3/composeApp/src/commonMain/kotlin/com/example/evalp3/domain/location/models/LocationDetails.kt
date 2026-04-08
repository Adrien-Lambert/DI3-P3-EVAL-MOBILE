package com.example.evalp3.domain.location.models

/**
 * Detailed representation of a location, including its residents.
 *
 * [residentCount] is a derived value from [residents] that provides
 * the number of residents without iterating through the list.
 */
data class LocationDetails(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<Resident>,
    val residentCount: Int
)
