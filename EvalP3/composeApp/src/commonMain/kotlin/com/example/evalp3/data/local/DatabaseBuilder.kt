package com.example.evalp3.data.local

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers

/**
 * Platform-specific Room database builder.
 * Each platform provides its own implementation via Koin modules.
 */
expect fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>

/**
 * Builds the [AppDatabase] instance from the platform-specific builder,
 * using the bundled SQLite driver and IO dispatcher for queries.
 */
fun buildAppDatabase(builder: RoomDatabase.Builder<AppDatabase>): AppDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
