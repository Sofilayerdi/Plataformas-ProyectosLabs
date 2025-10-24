package com.ayerdi.lab8.data.network.util

import com.ayerdi.lab8.CharacterEntity
import com.ayerdi.lab8.LocationEntity
import com.ayerdi.lab8.data.network.dto.CharacterDto
import com.ayerdi.lab8.data.network.dto.LocationDto

// Sofia Lopez - 231929

fun CharacterDto.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        image = image
    )
}

fun LocationDto.toEntity(): LocationEntity {
    return LocationEntity(
        id = id,
        name = name,
        type = type,
        dimension = dimension
    )
}