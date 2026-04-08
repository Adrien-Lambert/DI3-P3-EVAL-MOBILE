package com.example.evalp3.ui.screens.locationlist

import com.example.evalp3.domain.location.models.Location

data class LocationListState(
    val isLoading: Boolean = true,
    val locations: List<Location> = emptyList(),
    val error: String? = null
)
