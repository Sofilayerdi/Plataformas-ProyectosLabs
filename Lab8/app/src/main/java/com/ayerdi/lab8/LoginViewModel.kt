package com.ayerdi.lab8

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ayerdi.lab8.data.local.UserPreferences
import com.ayerdi.lab8.data.repository.DataStoreAuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Sofia Lopez - 231929

data class LoginState(
    val name: String = "",
    val isLoading: Boolean = false
)

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val userPreferences = UserPreferences(application)
    private val authRepository = DataStoreAuthRepository(userPreferences)

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onNameChange(name: String) {
        _state.update { it.copy(name = name) }
    }

    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true) }

                authRepository.saveUserName(_state.value.name)

                delay(2000)

                _state.update { it.copy(isLoading = false) }
                onSuccess()
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}