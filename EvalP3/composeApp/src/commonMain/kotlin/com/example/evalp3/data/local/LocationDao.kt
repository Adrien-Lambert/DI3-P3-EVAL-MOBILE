package com.example.evalp3.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.evalp3.data.local.objects.LocationObject
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for [LocationObject] entities.
 *
 * Provides reactive read access via [Flow] for the list,
 * and suspend functions for single reads and writes.
 */
@Dao
interface LocationDao {

    /** Returns all locations as a reactive Flow, emitting on every DB change. */
    @Query("SELECT * FROM LocationObject")
    fun getLocations(): Flow<List<LocationObject>>

    /** Returns a single location by [id], or null if not found. */
    @Query("SELECT * FROM LocationObject WHERE id = :id")
    suspend fun getLocation(id: Int): LocationObject?

    /** Inserts or updates the given locations (upsert strategy). */
    @Upsert
    suspend fun saveLocations(locations: List<LocationObject>)
}
