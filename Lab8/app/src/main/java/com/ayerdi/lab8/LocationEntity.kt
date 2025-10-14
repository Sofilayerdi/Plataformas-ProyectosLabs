package com.ayerdi.lab8

import androidx.room.Entity
import androidx.room.PrimaryKey

// Sofia Lopez - 231929

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)