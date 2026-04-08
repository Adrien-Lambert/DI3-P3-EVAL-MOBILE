package com.example.evalp3.data.remote

import com.example.evalp3.data.remote.responses.LocationResponse
import com.example.evalp3.data.remote.responses.PaginatedResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

/**
 * API client for the Rick and Morty location endpoints.
 */
class LocationApi(private val client: HttpClient) {

    suspend fun getLocations(page: Int? = null): PaginatedResponse<LocationResponse> {
        return client.get("location") {
            page?.let { parameter("page", it) }
        }.body()
    }

    suspend fun getLocation(id: Int): LocationResponse {
        return client.get("location/$id").body()
    }
}
