package com.example.evalp3.ui.screens.locationdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evalp3.common.SoundManager
import com.example.evalp3.domain.location.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi

/**
 * ViewModel for the location detail screen.
 *
 * Fetches the full location details (including residents) on initialization
 * and exposes a sealed [LocationDetailState] to represent loading, loaded
 * and error states in a type-safe way.
 * Plays a portal sound effect when the details are successfully loaded.
 */
class LocationDetailViewModel(
    private val locationId: Int,
    private val repository: LocationRepository,
    private val soundManager: SoundManager
) : ViewModel() {

    private val _state = MutableStateFlow<LocationDetailState>(LocationDetailState.Loading)
    val state: StateFlow<LocationDetailState> = _state.asStateFlow()

    init {
        loadLocationDetails()
    }

    @OptIn(ExperimentalResourceApi::class)
    private fun loadLocationDetails() {
        viewModelScope.launch {
            try {
                val details = repository.getLocationDetails(locationId)
                _state.value = LocationDetailState.Loaded(
                    name = details.name,
                    type = details.type,
                    dimension = details.dimension,
                    residents = details.residents,
                    residentCount = details.residentCount
                )
                val audioBytes = evalp3.composeapp.generated.resources.Res.readBytes(
                    "files/portal_sound.mp3"
                )
                soundManager.playSound(audioBytes)
            } catch (e: Exception) {
                _state.value = LocationDetailState.Error(
                    message = e.message ?: "Unknown error"
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        soundManager.release()
    }
}
