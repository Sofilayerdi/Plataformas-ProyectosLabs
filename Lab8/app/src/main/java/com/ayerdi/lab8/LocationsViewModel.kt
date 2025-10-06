package com.ayerdi.lab8

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationsViewModel : ViewModel() {
    private val locationDb = LocationDb()

    private val _state = MutableStateFlow(LocationsState())
    val state = _state.asStateFlow()

    init {
        loadLocations()
    }

    fun loadLocations() {
        _state.update {
            it.copy(
                isLoading = true,
                hasError = false,
                data = emptyList()
            )
        }

        viewModelScope.launch {
            delay(4000L) // 4 segundos de carga

            val randomNumber = (1..10).random()

            if (randomNumber % 2 == 0) {
                // Número par: éxito
                _state.update {
                    it.copy(
                        isLoading = false,
                        data = locationDb.getAllLocations(),
                        hasError = false
                    )
                }
            } else {
                // Número impar: error
                _state.update {
                    it.copy(
                        isLoading = false,
                        hasError = true,
                        data = emptyList()
                    )
                }
            }
        }
    }
}