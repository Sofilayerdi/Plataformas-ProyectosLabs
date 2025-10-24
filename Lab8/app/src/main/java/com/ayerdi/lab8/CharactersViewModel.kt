package com.ayerdi.lab8

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ayerdi.lab8.data.network.HttpClientFactory
import com.ayerdi.lab8.data.network.api.CharactersApi
import com.ayerdi.lab8.data.repository.LocalCharacterRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// Sofia Lopez - 231929

class CharactersViewModel(application: Application) : AndroidViewModel(application) {
    private val database = RickMortyDatabase.getDatabase(application)
    private val httpClient = HttpClientFactory.create()
    private val charactersApi = CharactersApi(httpClient)
    private val characterRepository = LocalCharacterRepository(
        database.characterDao(),
        charactersApi
    )

    val charactersFlow: StateFlow<List<CharacterSummary>> = characterRepository.getAllCharacters()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        syncCharacters()
    }

    private fun syncCharacters() {
        viewModelScope.launch {
            try {
                characterRepository.syncCharacters()
            } catch (e: Exception) {
            }
        }
    }

    fun deleteCharacter(id: Int) {
        viewModelScope.launch {
            try {
                database.characterDao().deleteCharacter(id)
            } catch (e: Exception) {
            }
        }
    }

    fun deleteAllCharacters() {
        viewModelScope.launch {
            try {
                database.characterDao().deleteAllCharacters()
                syncCharacters()
            } catch (e: Exception) {
            }
        }
    }

    fun insertDummyData() {
        viewModelScope.launch {
            try {
                val characterDb = CharacterDb()
                val dummyCharacters = characterDb.getAllCharacters()
                val entities = dummyCharacters.map { character ->
                    CharacterEntity(
                        id = character.id,
                        name = character.name,
                        status = character.status,
                        species = character.species,
                        gender = character.gender,
                        image = character.image
                    )
                }
                characterRepository.insertAllCharacters(entities)
            } catch (e: Exception) {
            }
        }
    }
}
