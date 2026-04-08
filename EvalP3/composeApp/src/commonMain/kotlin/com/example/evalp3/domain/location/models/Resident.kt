package com.example.evalp3.domain.location.models

/**
 * Simplified representation of a character living in a location.
 * Contains only the information needed for display purposes.
 */
data class Resident(
    val id: Int,
    val name: String,
    val avatarUrl: String
)
