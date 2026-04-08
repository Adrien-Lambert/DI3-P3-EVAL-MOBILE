package com.example.evalp3.data.remote

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.cio.CIO

actual fun createPlatformEngine(): HttpClientEngineFactory<*> = CIO
