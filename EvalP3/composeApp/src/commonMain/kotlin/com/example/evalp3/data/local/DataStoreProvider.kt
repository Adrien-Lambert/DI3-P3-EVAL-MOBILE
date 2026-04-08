package com.example.evalp3.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

internal const val DATA_STORE_FILE_NAME = "settings.preferences_pb"

/**
 * Platform-specific DataStore provider.
 * Used to persist lightweight key-value data such as the current pagination page.
 */
expect fun createDataStore(): DataStore<Preferences>
