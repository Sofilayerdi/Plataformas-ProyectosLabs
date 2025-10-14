package com.ayerdi.lab8

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// Sofia Lopez - 231929

data class LocationSummary(
    val id: Int,
    val name: String,
    val type: String
)

@Dao
interface LocationDao {
    @Query("SELECT id, name, type FROM locations")
    suspend fun getAllLocations(): List<LocationSummary>

    @Query("SELECT id, name, type FROM locations")
    fun getAllLocationsFlow(): Flow<List<LocationSummary>>

    @Query("SELECT * FROM locations WHERE id = :id")
    suspend fun getLocationById(id: Int): LocationEntity?

    @Query("DELETE FROM locations WHERE id = :id")
    suspend fun deleteLocation(id: Int)

    @Query("DELETE FROM locations")
    suspend fun deleteAllLocations()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLocations(locations: List<LocationEntity>)
}