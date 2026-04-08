package com.example.evalp3.ui.screens.locationdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.evalp3.ui.core.BackArrow
import com.example.evalp3.ui.core.ErrorView
import com.example.evalp3.ui.core.LoadingView
import com.example.evalp3.ui.core.ResidentCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDetailScreen(
    viewModel: LocationDetailViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (state) {
                            is LocationDetailState.Loaded -> (state as LocationDetailState.Loaded).name
                            else -> "Location"
                        }
                    )
                },
                navigationIcon = { BackArrow(onClick = onBack) }
            )
        },
        modifier = modifier
    ) { padding ->
        when (val s = state) {
            is LocationDetailState.Loading -> LoadingView(modifier = Modifier.padding(padding))
            is LocationDetailState.Error -> ErrorView(
                message = s.message,
                modifier = Modifier.padding(padding)
            )
            is LocationDetailState.Loaded -> LocationDetailContent(
                state = s,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

/**
 * Stateless content composable for the location detail.
 * Reusable across mobile (full screen) and desktop (panel).
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LocationDetailContent(
    state: LocationDetailState.Loaded,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = state.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = state.type,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = state.dimension,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Residents (${state.residentCount})",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(12.dp))

        if (state.residents.isEmpty()) {
            Text(
                text = "No known residents",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                state.residents.forEach { resident ->
                    ResidentCard(resident = resident)
                }
            }
        }
    }
}
