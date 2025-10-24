package com.ayerdi.lab8.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import com.ayerdi.lab8.data.network.dto.CharacterDto
import com.ayerdi.lab8.data.network.dto.CharacterResponseDto

// Sofia Lopez - 231929

class CharactersApi(
    private val httpClient: HttpClient
) {
    suspend fun getCharacters(): Result<List<CharacterDto>> {
        return try {
            val response = httpClient.get("character")
            val characterResponse: CharacterResponseDto = response.body()
            Result.success(characterResponse.results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCharacterById(id: Int): Result<CharacterDto> {
        return try {
            val response = httpClient.get("character/$id")
            val character: CharacterDto = response.body()
            Result.success(character)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}