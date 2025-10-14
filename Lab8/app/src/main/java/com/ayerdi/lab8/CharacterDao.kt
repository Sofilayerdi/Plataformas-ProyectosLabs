package com.ayerdi.lab8

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// Sofia Lopez - 231929

data class CharacterSummary(
    val id: Int,
    val name: String,
    val status: String,
    val species: String
)

@Dao
interface CharacterDao {
    @Query("SELECT id, name, status, species FROM characters")
    suspend fun getAllCharacters(): List<CharacterSummary>

    @Query("SELECT id, name, status, species FROM characters")
    fun getAllCharactersFlow(): Flow<List<CharacterSummary>>

    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getCharacterById(id: Int): CharacterEntity?

    @Query("DELETE FROM characters WHERE id = :id")
    suspend fun deleteCharacter(id: Int)

    @Query("DELETE FROM characters")
    suspend fun deleteAllCharacters()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacters(characters: List<CharacterEntity>)
}