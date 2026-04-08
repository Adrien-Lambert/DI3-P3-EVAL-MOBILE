package com.example.evalp3.ui

import com.example.evalp3.ui.screens.locationdetail.LocationDetailViewModel
import com.example.evalp3.ui.screens.locationlist.LocationListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { LocationListViewModel(get()) }
    viewModel { (locationId: Int) -> LocationDetailViewModel(locationId, get()) }
}
