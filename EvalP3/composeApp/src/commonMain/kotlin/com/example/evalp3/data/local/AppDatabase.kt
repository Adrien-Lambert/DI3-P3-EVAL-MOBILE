package com.example.evalp3.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.evalp3.data.local.objects.LocationObject

@Database(entities = [LocationObject::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}
