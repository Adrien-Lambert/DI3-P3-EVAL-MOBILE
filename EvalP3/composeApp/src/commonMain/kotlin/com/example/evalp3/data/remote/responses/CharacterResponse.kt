package com.example.evalp3.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class CharacterResponse(
    val id: Int,
    val name: String,
    val image: String
)
