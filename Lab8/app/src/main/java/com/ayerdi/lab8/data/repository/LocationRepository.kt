package com.ayerdi.lab8.data.repository

import com.ayerdi.lab8.Location
import com.ayerdi.lab8.LocationDao
import com.ayerdi.lab8.LocationEntity
import com.ayerdi.lab8.LocationSummary
import com.ayerdi.lab8.data.network.api.LocationsApi
import com.ayerdi.lab8.data.network.util.toEntity
import kotlinx.coroutines.flow.Flow

// Sofia Lopez - 231929

interface LocationRepository {
    suspend fun insertAllLocations(locations: List<LocationEntity>)
    fun getAllLocations(): Flow<List<LocationSummary>>
    suspend fun getLocationById(id: Int): Location?
    suspend fun syncLocations(): Result<Unit>
}

class LocalLocationRepository(
    private val locationDao: LocationDao,
    private val locationsApi: LocationsApi
) : LocationRepository {

    override suspend fun insertAllLocations(locations: List<LocationEntity>) {
        locationDao.insertAllLocations(locations)
    }

    override fun getAllLocations(): Flow<List<LocationSummary>> {
        return locationDao.getAllLocationsFlow()
    }

    override suspend fun getLocationById(id: Int): Location? {
        val entity = locationDao.getLocationById(id)
        return entity?.let {
            Location(
                id = it.id,
                name = it.name,
                type = it.type,
                dimension = it.dimension
            )
        }
    }

    override suspend fun syncLocations(): Result<Unit> {
        return try {
            val localLocations = locationDao.getAllLocations()

            if (localLocations.isEmpty()) {
                locationsApi.getLocations()
                    .onSuccess { locationDtos ->
                        val entities = locationDtos.map { it.toEntity() }
                        locationDao.insertAllLocations(entities)
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