package com.example.evalp3.ui.screens.locationlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.evalp3.ui.core.ErrorView
import com.example.evalp3.ui.core.LoadingView
import com.example.evalp3.ui.core.LocationCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationListScreen(
    viewModel: LocationListViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Locations") })
        },
        modifier = modifier
    ) { padding ->
        when {
            state.isLoading -> LoadingView(modifier = Modifier.padding(padding))
            state.error != null -> ErrorView(
                message = state.error!!,
                modifier = Modifier.padding(padding)
            )
            else -> LocationListContent(
                state = state,
                onAction = viewModel::onAction,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

/**
 * Stateless content composable for the location list.
 * Reusable across mobile (full screen) and desktop (panel).
 */
@Composable
fun LocationListContent(
    state: LocationListState,
    onAction: (LocationListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(state.locations, key = { it.id }) { location ->
            LocationCard(
                location = location,
                onClick = { onAction(LocationListAction.SelectLocation(location.id)) }
            )
        }
    }
}
