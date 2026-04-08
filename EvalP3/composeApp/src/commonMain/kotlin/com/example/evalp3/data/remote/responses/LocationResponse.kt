package com.example.evalp3.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class LocationResponse(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
)
