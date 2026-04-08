package com.example.evalp3.common

import android.content.Context
import com.example.evalp3.data.local.createDataStore
import com.example.evalp3.data.local.getDatabaseBuilder

/**
 * Creates a [SoundManager] instance tied to the Android platform.
 * Provides an idiomatic way to initialize the audio component
 * from an Android [Context].
 */
fun Context.createSoundManager(): SoundManager {
    return SoundManager()
}

/**
 * Creates all platform-specific dependencies from this [Context].
 * Used by the Koin Android module to provide the database builder,
 * DataStore and SoundManager in a single idiomatic call.
 */
fun Context.createDatabaseBuilder() = getDatabaseBuilder(this)

fun Context.createPreferencesDataStore() = createDataStore(this)
