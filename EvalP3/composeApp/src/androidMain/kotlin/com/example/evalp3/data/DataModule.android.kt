package com.example.evalp3.data

import com.example.evalp3.common.createDatabaseBuilder
import com.example.evalp3.common.createPreferencesDataStore
import com.example.evalp3.common.createSoundManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val platformModule = module {
    single { androidContext().createDatabaseBuilder() }
    single { androidContext().createPreferencesDataStore() }
    single { androidContext().createSoundManager() }
}
