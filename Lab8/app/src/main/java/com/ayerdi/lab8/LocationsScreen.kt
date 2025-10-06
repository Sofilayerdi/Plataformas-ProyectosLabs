package com.ayerdi.lab8

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun LocationsScreen(
    onLocationClick: (Int) -> Unit = {},
    viewModel: LocationsViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LocationsScreenContent(
        state = state,
        onLocationClick = onLocationClick,
        onRetry = { viewModel.loadLocations() },
        modifier = modifier
    )
}

@Composable
private fun LocationsScreenContent(
    state: LocationsState,
    onLocationClick: (Int) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.primaryContainer)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Locations",
                    fontSize = 24.sp
                )
            }

            // Content según estado
            when {
                state.isLoading -> {
                    LoadingScreen(modifier = Modifier.fillMaxSize())
                }
                state.hasError -> {
                    ErrorScreen(
                        onRetry = onRetry,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.data) { location ->
                            LocationItem(
                                location = location,
                                onClick = onLocationClick
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LocationItem(
    location: Location,
    onClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(location.id) }
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = location.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = location.type,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
            )
        }
    }
}

@Composable
fun LocationDetailsScreen(
    locationId: Int,
    onBack: () -> Unit = {},
    viewModel: LocationDetailViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LocationDetailsContent(
        state = state,
        onBack = onBack,
        onRetry = { viewModel.loadLocation() },
        modifier = modifier
    )
}

@Composable
private fun LocationDetailsContent(
    state: LocationDetailState,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.primaryContainer)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = colors.onPrimaryContainer
                    )
                }
                Text(
                    text = "Location Details",
                    fontSize = 24.sp
                )
            }

            // Content según estado
            when {
                state.isLoading -> {
                    LoadingScreen(modifier = Modifier.fillMaxSize())
                }
                state.hasError -> {
                    ErrorScreen(
                        onRetry = onRetry,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                state.data != null -> {
                    val location = state.data
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = location.name,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                            .padding(horizontal = 50.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        DetailRow("ID:", location.id.toString())
                        DetailRow("Type:", location.type)
                        DetailRow("Dimension:", location.dimension)
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light
        )
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light
        )
    }
}
@Preview(showBackground = true)
@Composable
private fun PreviewLocationDetailsScreen() {
    LocationDetailsContent(
        state = LocationDetailState(
            isLoading = false,
            data = Location(1, "Earth (C-137)", "Planet", "Dimension C-137"),
            hasError = false
        ),
        onBack = {},
        onRetry = {}
    )
}