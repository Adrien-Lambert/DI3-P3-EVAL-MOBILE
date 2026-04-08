package com.example.evalp3.data.local.objects

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationObject(
    @PrimaryKey val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residentsIds: String,
    val created: String
)
