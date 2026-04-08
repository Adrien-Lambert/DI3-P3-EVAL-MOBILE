package com.example.evalp3.ui.screens.locationlist

sealed interface LocationListAction {
    data class SelectLocation(val id: Int) : LocationListAction
}
