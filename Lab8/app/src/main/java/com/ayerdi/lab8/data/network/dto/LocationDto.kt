package com.ayerdi.lab8.data.network.dto

import kotlinx.serialization.Serializable

// Sofia Lopez - 231929

@Serializable
data class LocationDto(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String> = emptyList(),
    val url: String,
    val created: String
)

@Serializable
data class LocationResponseDto(
    val info: InfoDto,
    val results: List<LocationDto>
)