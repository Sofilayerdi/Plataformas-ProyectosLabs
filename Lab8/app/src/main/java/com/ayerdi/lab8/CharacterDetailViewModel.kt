package com.ayerdi.lab8

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ayerdi.lab8.data.network.HttpClientFactory
import com.ayerdi.lab8.data.network.api.CharactersApi
import com.ayerdi.lab8.data.repository.LocalCharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Sofia Lopez - 231929

class CharacterDetailViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {
    private val database = RickMortyDatabase.getDatabase(application)
    private val httpClient = HttpClientFactory.create()
    private val charactersApi = CharactersApi(httpClient)
    private val characterRepository = LocalCharacterRepository(
        database.characterDao(),
        charactersApi
    )

    private val _state = MutableStateFlow(CharacterDetailState(isLoading = true))
    val state: StateFlow<CharacterDetailState> = _state.asStateFlow()

    private val characterId: Int = savedStateHandle.toRoute<CharacterDetails>().characterId

    init {
        loadCharacter()
    }


    fun loadCharacter() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, hasError = false) }

                val character = characterRepository.getCharacterById(characterId)

                if (character != null) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            hasError = false,
                            data = character
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            hasError = true,
                            data = null
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        hasError = true
                    )
                }
            }
        }
    }

    fun updateCharacter(character: Character) {
        viewModelScope.launch {
            try {
                val entity = CharacterEntity(
                    id = character.id,
                    name = character.name,
                    status = character.status,
                    species = character.species,
                    gender = character.gender,
                    image = character.image
                )
                database.characterDao().insertCharacter(entity)
                _state.update { it.copy(data = character) }
            } catch (e: Exception) {
                _state.update { it.copy(hasError = true) }
            }
        }
    }
}