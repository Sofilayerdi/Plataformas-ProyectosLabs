package com.ayerdi.lab8

data class LocationsState(
    val isLoading: Boolean = true,
    val data: List<Location> = emptyList(),
    val hasError: Boolean = false
)

data class LocationDetailState(
    val isLoading: Boolean = true,
    val data: Location? = null,
    val hasError: Boolean = false
)