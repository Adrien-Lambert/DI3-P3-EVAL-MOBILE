package com.example.evalp3.data.local.objects

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity representing a cached location from the API.
 *
 * [residentsIds] stores character IDs as a comma-separated string
 * (e.g. "1,2,38") to avoid a complex TypeConverter or join table.
 */
@Entity
data class LocationObject(
    @PrimaryKey val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residentsIds: String,
    val created: String
)
