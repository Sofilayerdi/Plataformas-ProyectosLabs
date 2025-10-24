package com.ayerdi.lab8

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ayerdi.lab8.data.network.HttpClientFactory
import com.ayerdi.lab8.data.network.api.LocationsApi
import com.ayerdi.lab8.data.repository.LocalLocationRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// Sofia Lopez - 231929

class LocationsViewModel(application: Application) : AndroidViewModel(application) {
    private val database = RickMortyDatabase.getDatabase(application)
    private val httpClient = HttpClientFactory.create()
    private val locationsApi = LocationsApi(httpClient)
    private val locationRepository = LocalLocationRepository(
        database.locationDao(),
        locationsApi
    )

    val locationsFlow: StateFlow<List<LocationSummary>> = locationRepository.getAllLocations()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        syncLocations()
    }

    private fun syncLocations() {
        viewModelScope.launch {
            try {
                locationRepository.syncLocations()
            } catch (e: Exception) {
            }
        }
    }

    fun deleteLocation(id: Int) {
        viewModelScope.launch {
            try {
                database.locationDao().deleteLocation(id)
            } catch (e: Exception) {
            }
        }
    }

    fun deleteAllLocations() {
        viewModelScope.launch {
            try {
                database.locationDao().deleteAllLocations()
                syncLocations()
            } catch (e: Exception) {
            }
        }
    }

    fun insertDummyData() {
        viewModelScope.launch {
            try {
                val locationDb = LocationDb()
                val dummyLocations = locationDb.getAllLocations()
                val entities = dummyLocations.map { location ->
                    LocationEntity(
                        id = location.id,
                        name = location.name,
                        type = location.type,
                        dimension = location.dimension
                    )
                }
                locationRepository.insertAllLocations(entities)
            } catch (e: Exception) {
            }
        }
    }
}