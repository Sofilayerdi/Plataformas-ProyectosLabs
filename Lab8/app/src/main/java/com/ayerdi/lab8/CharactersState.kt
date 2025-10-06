package com.ayerdi.lab8

data class CharactersState(
    val isLoading: Boolean = true,
    val data: List<Character> = emptyList(),
    val hasError: Boolean = false
)

data class CharacterDetailState(
    val isLoading: Boolean = true,
    val data: Character? = null,
    val hasError: Boolean = false
)