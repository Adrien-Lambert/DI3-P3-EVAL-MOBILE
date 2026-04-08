package com.example.evalp3.common

import com.example.evalp3.data.databaseModule
import com.example.evalp3.data.remoteModule
import com.example.evalp3.data.repositoriesModule
import org.koin.core.module.Module

/**
 * Returns the shared Koin modules used across all platforms.
 * Platform-specific modules (platformModule) must be added by each platform.
 */
fun sharedModules(): List<Module> = listOf(
    remoteModule,
    databaseModule,
    repositoriesModule
)
