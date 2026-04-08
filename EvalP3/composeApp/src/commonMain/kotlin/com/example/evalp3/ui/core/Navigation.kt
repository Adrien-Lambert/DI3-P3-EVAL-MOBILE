package com.example.evalp3.ui.core

import kotlinx.serialization.Serializable

/**
 * Type-safe navigation destinations for the app.
 * Used by Navigation Compose for route resolution.
 */
sealed interface Destination {

    @Serializable
    data object LocationList : Destination

    @Serializable
    data class LocationDetail(val locationId: Int) : Destination
}
