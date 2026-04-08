package com.example.evalp3.data.remote

import com.example.evalp3.data.remote.responses.CharacterResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

/**
 * API client for the Rick and Morty character endpoints.
 * Used to fetch resident details for a given location.
 */
class CharacterApi(private val client: HttpClient) {

    private val json = Json { ignoreUnknownKeys = true }

    /**
     * Fetches characters by their IDs.
     *
     * The Rick and Morty API returns a single JSON object when only one ID
     * is requested, and a JSON array for multiple IDs. This method handles
     * both cases transparently.
     *
     * @param ids comma-separated character IDs (e.g. "1,2,3")
     * @return list of character responses
     */
    suspend fun getCharactersByIds(ids: String): List<CharacterResponse> {
        val response = client.get("character/$ids")
        val text = response.bodyAsText()

        return if (text.trimStart().startsWith("[")) {
            json.decodeFromString<List<CharacterResponse>>(text)
        } else {
            listOf(json.decodeFromString<CharacterResponse>(text))
        }
    }
}
