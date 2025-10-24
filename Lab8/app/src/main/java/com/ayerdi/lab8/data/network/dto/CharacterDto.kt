package com.ayerdi.lab8.data.network.dto

import kotlinx.serialization.Serializable

// Sofia Lopez - 231929

@Serializable
data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String = "",
    val gender: String,
    val origin: OriginDto,
    val location: LocationLinkDto,
    val image: String,
    val episode: List<String> = emptyList(),
    val url: String,
    val created: String
)

@Serializable
data class OriginDto(
    val name: String,
    val url: String
)

@Serializable
data class LocationLinkDto(
    val name: String,
    val url: String
)

@Serializable
data class CharacterResponseDto(
    val info: InfoDto,
    val results: List<CharacterDto>
)

@Serializable
data class InfoDto(
    val count: Int,
    val pages: Int,
    val next: String? = null,
    val prev: String? = null
)