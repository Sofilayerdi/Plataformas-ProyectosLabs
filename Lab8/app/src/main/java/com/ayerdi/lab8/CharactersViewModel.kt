package com.ayerdi.lab8

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// Sofia Lopez - 231929

class CharactersViewModel(application: Application) : AndroidViewModel(application) {
    private val database = RickMortyDatabase.getDatabase(application)
    private val characterDao = database.characterDao()
    private val characterDb = CharacterDb()

    val charactersFlow: StateFlow<List<CharacterSummary>> = characterDao.getAllCharactersFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteCharacter(id: Int) {
        viewModelScope.launch {
            try {
                characterDao.deleteCharacter(id)
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    fun deleteAllCharacters() {
        viewModelScope.launch {
            try {
                characterDao.deleteAllCharacters()
            } catch (e: Exception) {
            }
        }
    }

    fun insertDummyData() {
        viewModelScope.launch {
            try {
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
                characterDao.insertAllCharacters(entities)
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }
}