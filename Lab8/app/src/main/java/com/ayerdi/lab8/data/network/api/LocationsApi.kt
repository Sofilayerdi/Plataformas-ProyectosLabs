package com.ayerdi.lab8.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import com.ayerdi.lab8.data.network.dto.LocationDto
import com.ayerdi.lab8.data.network.dto.LocationResponseDto

// Sofia Lopez - 231929

class LocationsApi(
    private val httpClient: HttpClient
) {
    suspend fun getLocations(): Result<List<LocationDto>> {
        return try {
            val response = httpClient.get("location")
            val locationResponse: LocationResponseDto = response.body()
            Result.success(locationResponse.results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getLocationById(id: Int): Result<LocationDto> {
        return try {
            val response = httpClient.get("location/$id")
            val location: LocationDto = response.body()
            Result.success(location)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}