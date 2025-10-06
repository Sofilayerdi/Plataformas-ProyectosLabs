package com.ayerdi.lab8

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val characterDb = CharacterDb()

    // Obtener el ID desde SavedStateHandle
    private val characterId: Int = savedStateHandle.toRoute<CharacterDetails>().characterId

    private val _state = MutableStateFlow(CharacterDetailState())
    val state = _state.asStateFlow()

    init {
        loadCharacter()
    }

    fun loadCharacter() {
        _state.update {
            it.copy(
                isLoading = true,
                hasError = false,
                data = null
            )
        }

        viewModelScope.launch {
            delay(2000L) // 2 segundos de carga

            val randomNumber = (1..10).random()

            if (randomNumber % 2 == 0) {
                // Número par: éxito
                val character = characterDb.getCharacterById(characterId)
                _state.update {
                    it.copy(
                        isLoading = false,
                        data = character,
                        hasError = false
                    )
                }
            } else {
                // Número impar: error
                _state.update {
                    it.copy(
                        isLoading = false,
                        hasError = true,
                        data = null
                    )
                }
            }
        }
    }
}