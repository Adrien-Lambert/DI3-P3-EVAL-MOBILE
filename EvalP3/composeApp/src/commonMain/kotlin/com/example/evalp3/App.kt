package com.example.evalp3

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.evalp3.ui.core.Destination
import com.example.evalp3.ui.core.EvalP3Theme
import com.example.evalp3.ui.screens.locationdetail.LocationDetailScreen
import com.example.evalp3.ui.screens.locationdetail.LocationDetailViewModel
import com.example.evalp3.ui.screens.locationlist.LocationListEvent
import com.example.evalp3.ui.screens.locationlist.LocationListScreen
import com.example.evalp3.ui.screens.locationlist.LocationListViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun App() {
    EvalP3Theme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Destination.LocationList
            ) {
                composable<Destination.LocationList> {
                    val viewModel = koinViewModel<LocationListViewModel>()

                    LaunchedEffect(Unit) {
                        viewModel.events.collect { event ->
                            when (event) {
                                is LocationListEvent.NavigateToDetail -> {
                                    navController.navigate(
                                        Destination.LocationDetail(event.locationId)
                                    )
                                }
                            }
                        }
                    }

                    LocationListScreen(viewModel = viewModel)
                }

                composable<Destination.LocationDetail> { backStackEntry ->
                    val route = backStackEntry.toRoute<Destination.LocationDetail>()
                    val viewModel = koinViewModel<LocationDetailViewModel> {
                        parametersOf(route.locationId)
                    }

                    LocationDetailScreen(
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
