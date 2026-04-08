package com.example.evalp3.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.evalp3.data.local.objects.LocationObject
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Query("SELECT * FROM LocationObject")
    fun getLocations(): Flow<List<LocationObject>>

    @Query("SELECT * FROM LocationObject WHERE id = :id")
    suspend fun getLocation(id: Int): LocationObject?

    @Upsert
    suspend fun saveLocations(locations: List<LocationObject>)
}
