package com.example.evalp3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.evalp3.ui.core.EvalP3Theme
import com.example.evalp3.ui.core.ErrorView
import com.example.evalp3.ui.core.LoadingView
import com.example.evalp3.ui.screens.locationdetail.LocationDetailContent
import com.example.evalp3.ui.screens.locationdetail.LocationDetailState
import com.example.evalp3.ui.screens.locationdetail.LocationDetailViewModel
import com.example.evalp3.ui.screens.locationlist.LocationListAction
import com.example.evalp3.ui.screens.locationlist.LocationListContent
import com.example.evalp3.ui.screens.locationlist.LocationListViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

/**
 * Desktop-specific layout using a master-detail pattern.
 * The left panel shows the location list (1/3 width),
 * the right panel shows the selected location's details (2/3 width).
 */
@Composable
fun DesktopApp() {
    EvalP3Theme {
        Surface(modifier = Modifier.fillMaxSize()) {
            var selectedLocationId by remember { mutableStateOf<Int?>(null) }

            Row(modifier = Modifier.fillMaxSize()) {
                // Left panel: location list
                DesktopListPanel(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    onLocationSelected = { selectedLocationId = it }
                )

                HorizontalDivider(
                    modifier = Modifier.fillMaxHeight().width(1.dp)
                )

                // Right panel: location detail or placeholder
                if (selectedLocationId != null) {
                    DesktopDetailPanel(
                        modifier = Modifier.weight(2f).fillMaxHeight(),
                        locationId = selectedLocationId!!
                    )
                } else {
                    Box(
                        modifier = Modifier.weight(2f).fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Select a location",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DesktopListPanel(
    modifier: Modifier = Modifier,
    onLocationSelected: (Int) -> Unit
) {
    val viewModel = koinViewModel<LocationListViewModel>()
    val state by viewModel.state.collectAsState()

    when {
        state.isLoading -> LoadingView(modifier = modifier)
        state.error != null -> ErrorView(message = state.error!!, modifier = modifier)
        else -> LocationListContent(
            state = state,
            onAction = { action ->
                if (action is LocationListAction.SelectLocation) {
                    onLocationSelected(action.id)
                }
            },
            modifier = modifier
        )
    }
}

@Composable
private fun DesktopDetailPanel(
    modifier: Modifier = Modifier,
    locationId: Int
) {
    val viewModel = koinViewModel<LocationDetailViewModel>(
        key = locationId.toString()
    ) { parametersOf(locationId) }
    val state by viewModel.state.collectAsState()

    when (val s = state) {
        is LocationDetailState.Loading -> LoadingView(modifier = modifier)
        is LocationDetailState.Error -> ErrorView(message = s.message, modifier = modifier)
        is LocationDetailState.Loaded -> LocationDetailContent(state = s, modifier = modifier)
    }
}
