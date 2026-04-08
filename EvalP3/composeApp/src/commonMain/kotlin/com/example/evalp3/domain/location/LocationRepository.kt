package com.example.evalp3.domain.location

import com.example.evalp3.domain.location.models.Location
import com.example.evalp3.domain.location.models.LocationDetails
import kotlinx.coroutines.flow.Flow

/**
 * Data access contract for locations.
 *
 * Defines available operations to retrieve locations independently
 * of the data source (API, local database, cache).
 * The concrete implementation lives in the Data layer.
 */
interface LocationRepository {

    /**
     * Retrieves the list of all locations as a reactive [Flow].
     *
     * The Flow allows the Presentation layer to automatically receive
     * updates when local data changes.
     *
     * @return a continuous stream of the location list
     */
    fun getLocations(): Flow<List<Location>>

    /**
     * Retrieves the full details of a location by its identifier,
     * including the list of its residents.
     *
     * @param id unique identifier of the location
     * @return the location details with its residents
     */
    suspend fun getLocationDetails(id: Int): LocationDetails
}
