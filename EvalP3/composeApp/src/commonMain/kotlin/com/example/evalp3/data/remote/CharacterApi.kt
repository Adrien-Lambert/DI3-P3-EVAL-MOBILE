package com.example.evalp3.data.remote

import com.example.evalp3.data.remote.responses.CharacterResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

/**
 * API client for the Rick and Morty character endpoints.
 * Used to fetch resident details for a given location.
 */
class CharacterApi(private val client: HttpClient) {

    /**
     * Fetches multiple characters by their IDs.
     *
     * @param ids comma-separated character IDs (e.g. "1,2,3")
     * @return list of character responses
     */
    suspend fun getCharactersByIds(ids: String): List<CharacterResponse> {
        return client.get("character/$ids").body()
    }
}
