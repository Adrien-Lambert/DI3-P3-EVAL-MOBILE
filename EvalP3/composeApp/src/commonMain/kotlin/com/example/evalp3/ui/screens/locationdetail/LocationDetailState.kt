package com.example.evalp3.ui.screens.locationdetail

import com.example.evalp3.domain.location.models.Resident

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
