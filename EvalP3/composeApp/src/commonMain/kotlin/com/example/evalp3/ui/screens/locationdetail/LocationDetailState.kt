package com.example.evalp3.ui.screens.locationdetail

import com.example.evalp3.domain.location.models.Resident

/**
 * Type-safe UI state for the location detail screen.
 *
 * Uses a sealed interface to represent mutually exclusive states,
 * ensuring exhaustive handling in the UI via `when` expressions.
 */
sealed interface LocationDetailState {
    data object Loading : LocationDetailState
    data class Loaded(
        val name: String,
        val type: String,
        val dimension: String,
        val residents: List<Resident>,
        val residentCount: Int
    ) : LocationDetailState
    data class Error(val message: String) : LocationDetailState
}
