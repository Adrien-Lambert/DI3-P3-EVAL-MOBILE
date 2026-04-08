package com.example.evalp3.data.remote

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.java.Java

actual fun createPlatformEngine(): HttpClientEngineFactory<*> = Java
