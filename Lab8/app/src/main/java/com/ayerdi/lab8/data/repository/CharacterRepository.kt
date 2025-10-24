package com.ayerdi.lab8.data.repository

import com.ayerdi.lab8.Character
import com.ayerdi.lab8.CharacterDao
import com.ayerdi.lab8.CharacterEntity
import com.ayerdi.lab8.CharacterSummary
import com.ayerdi.lab8.data.network.api.CharactersApi
import com.ayerdi.lab8.data.network.util.toEntity
import kotlinx.coroutines.flow.Flow

// Sofia Lopez - 231929

interface CharacterRepository {
    suspend fun insertAllCharacters(characters: List<CharacterEntity>)
    fun getAllCharacters(): Flow<List<CharacterSummary>>
    suspend fun getCharacterById(id: Int): Character?
    suspend fun syncCharacters(): Result<Unit>
}

class LocalCharacterRepository(
    private val characterDao: CharacterDao,
    private val charactersApi: CharactersApi
) : CharacterRepository {

    override suspend fun insertAllCharacters(characters: List<CharacterEntity>) {
        characterDao.insertAllCharacters(characters)
    }

    override fun getAllCharacters(): Flow<List<CharacterSummary>> {
        return characterDao.getAllCharactersFlow()
    }

    override suspend fun getCharacterById(id: Int): Character? {
        val entity = characterDao.getCharacterById(id)
        return entity?.let {
            Character(
                id = it.id,
                name = it.name,
                status = it.status,
                species = it.species,
                gender = it.gender,
                image = it.image
            )
        }
    }

    override suspend fun syncCharacters(): Result<Unit> {
        return try {
            val localCharacters = characterDao.getAllCharacters()

            if (localCharacters.isEmpty()) {
                charactersApi.getCharacters()
                    .onSuccess { characterDtos ->
                        val entities = characterDtos.map { it.toEntity() }
                        characterDao.insertAllCharacters(entities)
                    }
                    .onFailure { error ->
                        return Result.failure(error)
                    }
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
