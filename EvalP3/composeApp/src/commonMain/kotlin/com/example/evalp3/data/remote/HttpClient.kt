package com.example.evalp3.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

expect fun createPlatformEngine(): HttpClientEngineFactory<*>

/**
 * Creates a configured [HttpClient] with JSON content negotiation
 * and a default base URL pointing to the Rick and Morty API.
 */
fun createHttpClient(): HttpClient {
    return HttpClient(createPlatformEngine()) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        defaultRequest {
            url("https://rickandmortyapi.com/api/")
        }
    }
}
