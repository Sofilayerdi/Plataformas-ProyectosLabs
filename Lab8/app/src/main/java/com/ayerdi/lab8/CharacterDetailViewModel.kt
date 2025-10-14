package com.ayerdi.lab8

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
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
    private val characterDao = database.characterDao()

    private val _state = MutableStateFlow(CharacterDetailState(isLoading = true))
    val state: StateFlow<CharacterDetailState> = _state.asStateFlow()

    private val characterId: Int = savedStateHandle.toRoute<CharacterDetails>().characterId

    init {
        loadCharacter()
    }

    public fun loadCharacter() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, hasError = false) }
                val characterEntity = characterDao.getCharacterById(characterId)

                if (characterEntity != null) {
                    val character = Character(
                        id = characterEntity.id,
                        name = characterEntity.name,
                        status = characterEntity.status,
                        species = characterEntity.species,
                        gender = characterEntity.gender,
                        image = characterEntity.image
                    )
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
                characterDao.insertCharacter(entity)
                _state.update { it.copy(data = character) }
            } catch (e: Exception) {
                _state.update { it.copy(hasError = true) }
            }
        }
    }
}