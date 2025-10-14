package com.ayerdi.lab8

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// Sofia Lopez - 231929

class LocationsViewModel(application: Application) : AndroidViewModel(application) {
    private val database = RickMortyDatabase.getDatabase(application)
    private val locationDao = database.locationDao()
    private val locationDb = LocationDb()

    val locationsFlow: StateFlow<List<LocationSummary>> = locationDao.getAllLocationsFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteLocation(id: Int) {
        viewModelScope.launch {
            try {
                locationDao.deleteLocation(id)
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    fun deleteAllLocations() {
        viewModelScope.launch {
            try {
                locationDao.deleteAllLocations()
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    fun insertDummyData() {
        viewModelScope.launch {
            try {
                val dummyLocations = locationDb.getAllLocations()
                val entities = dummyLocations.map { location ->
                    LocationEntity(
                        id = location.id,
                        name = location.name,
                        type = location.type,
                        dimension = location.dimension
                    )
                }
                locationDao.insertAllLocations(entities)
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }
}