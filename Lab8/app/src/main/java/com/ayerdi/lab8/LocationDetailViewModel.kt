package com.ayerdi.lab8

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ayerdi.lab8.data.network.HttpClientFactory
import com.ayerdi.lab8.data.network.api.LocationsApi
import com.ayerdi.lab8.data.repository.LocalLocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Sofia Lopez - 231929

class LocationDetailViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {
    private val database = RickMortyDatabase.getDatabase(application)
    private val httpClient = HttpClientFactory.create()
    private val locationsApi = LocationsApi(httpClient)
    private val locationRepository = LocalLocationRepository(
        database.locationDao(),
        locationsApi
    )

    private val _state = MutableStateFlow(LocationDetailState(isLoading = true))
    val state: StateFlow<LocationDetailState> = _state.asStateFlow()

    private val locationId: Int = savedStateHandle.toRoute<LocationDetails>().locationId

    init {
        loadLocation()
    }


    fun loadLocation() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, hasError = false) }

                val location = locationRepository.getLocationById(locationId)

                if (location != null) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            hasError = false,
                            data = location
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            hasError = true,
                            data = null
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        hasError = true
                    )
                }
            }
        }
    }

    fun updateLocation(location: Location) {
        viewModelScope.launch {
            try {
                val entity = LocationEntity(
                    id = location.id,
                    name = location.name,
                    type = location.type,
                    dimension = location.dimension
                )
                database.locationDao().insertLocation(entity)
                _state.update { it.copy(data = location) }
            } catch (e: Exception) {
                _state.update { it.copy(hasError = true) }
            }
        }
    }
}