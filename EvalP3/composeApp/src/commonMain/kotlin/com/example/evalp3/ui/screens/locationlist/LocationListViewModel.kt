package com.example.evalp3.ui.screens.locationlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evalp3.domain.location.LocationRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the location list screen following the UDF pattern.
 *
 * Exposes a single [state] flow that the UI observes, and processes
 * user interactions through [onAction]. Navigation events are emitted
 * via [events] to keep the ViewModel unaware of the navigation framework.
 */
class LocationListViewModel(
    private val repository: LocationRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LocationListState())
    val state: StateFlow<LocationListState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<LocationListEvent>()
    val events = _events.asSharedFlow()

    init {
        loadLocations()
    }

    fun onAction(action: LocationListAction) {
        when (action) {
            is LocationListAction.SelectLocation -> {
                viewModelScope.launch {
                    _events.emit(LocationListEvent.NavigateToDetail(action.id))
                }
            }
        }
    }

    private fun loadLocations() {
        viewModelScope.launch {
            repository.getLocations()
                .onStart { _state.update { it.copy(isLoading = true, error = null) } }
                .catch { e -> _state.update { it.copy(isLoading = false, error = e.message) } }
                .collect { locations ->
                    _state.update { it.copy(isLoading = false, locations = locations, error = null) }
                }
        }
    }
}

sealed interface LocationListEvent {
    data class NavigateToDetail(val locationId: Int) : LocationListEvent
}
