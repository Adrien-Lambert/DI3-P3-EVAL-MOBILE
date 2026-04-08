package com.example.evalp3.data

import com.example.evalp3.common.SoundManager
import com.example.evalp3.data.local.createDataStore
import com.example.evalp3.data.local.getDatabaseBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val platformModule = module {
    single { getDatabaseBuilder(androidContext()) }
    single { createDataStore(androidContext()) }
    single { SoundManager() }
}
