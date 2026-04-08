package com.example.evalp3.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.evalp3.data.local.LocationDao
import com.example.evalp3.data.local.toDBObject
import com.example.evalp3.data.local.toDetailedModel
import com.example.evalp3.data.local.toModel
import com.example.evalp3.data.local.toResident
import com.example.evalp3.data.remote.CharacterApi
import com.example.evalp3.data.remote.LocationApi
import com.example.evalp3.domain.location.LocationRepository
import com.example.evalp3.domain.location.models.Location
import com.example.evalp3.domain.location.models.LocationDetails
import com.example.evalp3.domain.location.models.Resident
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
 * Local-first implementation of [LocationRepository].
 *
 * Fetch strategy:
 * 1. Expose a reactive [Flow] from the local Room database.
 * 2. On first subscription, check if local data is empty.
 * 3. If empty, fetch all pages from the API, save to Room, and let the
 *    Flow emit the updated data automatically.
 * 4. For details, fetch the location from local DB then resolve residents
 *    via the character API.
 *
 * This ensures the UI always reads from a single source of truth (Room)
 * while the network layer only serves as a data synchronization mechanism.
 */
class LocationRepositoryImpl(
    private val locationApi: LocationApi,
    private val characterApi: CharacterApi,
    private val locationDao: LocationDao,
    private val dataStore: DataStore<Preferences>
) : LocationRepository {

    companion object {
        private val LAST_PAGE_KEY = intPreferencesKey("last_fetched_page")
    }

    override fun getLocations(): Flow<List<Location>> {
        return locationDao.getLocations()
            .onStart { fetchLocationsIfEmpty() }
            .map { objects -> objects.map { it.toModel() } }
    }

    override suspend fun getLocationDetails(id: Int): LocationDetails {
        val locationObject = locationDao.getLocation(id)
            ?: throw IllegalArgumentException("Location $id not found")

        val residents = fetchResidents(locationObject.residentsIds)
        return locationObject.toDetailedModel(residents)
    }

    /**
     * Fetches all location pages from the API and saves them locally
     * if the local database is empty.
     */
    private suspend fun fetchLocationsIfEmpty() {
        val localData = locationDao.getLocations().first()
        if (localData.isNotEmpty()) return

        var page = 1
        var totalPages: Int

        do {
            val response = locationApi.getLocations(page)
            val objects = response.results.map { it.toDBObject() }
            locationDao.saveLocations(objects)
            totalPages = response.info.pages
            saveLastPage(page)
            page++
        } while (page <= totalPages)
    }

    /**
     * Fetches resident characters from the API by their comma-separated IDs.
     * Returns an empty list if there are no residents.
     */
    private suspend fun fetchResidents(residentsIds: String): List<Resident> {
        if (residentsIds.isBlank()) return emptyList()
        return characterApi.getCharactersByIds(residentsIds)
            .map { it.toResident() }
    }

    private suspend fun saveLastPage(page: Int) {
        dataStore.edit { prefs ->
            prefs[LAST_PAGE_KEY] = page
        }
    }
}
