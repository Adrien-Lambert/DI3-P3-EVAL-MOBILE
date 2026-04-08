package com.example.evalp3.data.local

import androidx.room.RoomDatabase

/**
 * Platform-specific Room database builder.
 * Each platform provides the correct context and file path.
 */
expect fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>
