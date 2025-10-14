package com.ayerdi.lab8

import androidx.room.Entity
import androidx.room.PrimaryKey

// Sofia Lopez - 231929

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String
)