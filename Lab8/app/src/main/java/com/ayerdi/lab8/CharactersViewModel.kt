package com.ayerdi.lab8


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharactersViewModel : ViewModel() {
    private val characterDb = CharacterDb()

    private val _state = MutableStateFlow(CharactersState())
    val state = _state.asStateFlow()

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        _state.update {
            it.copy(
                isLoading = true,
                hasError = false,
                data = emptyList()
            )
        }

        viewModelScope.launch {
            delay(4000L)

            val randomNumber = (1..10).random()

            if (randomNumber % 2 == 0) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        data = characterDb.getAllCharacters(),
                        hasError = false
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        isLoading = false,
                        hasError = true,
                        data = emptyList()
                    )
                }
            }
        }
    }
}