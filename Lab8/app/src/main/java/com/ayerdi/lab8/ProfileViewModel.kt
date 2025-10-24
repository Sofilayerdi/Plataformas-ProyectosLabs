package com.ayerdi.lab8

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ayerdi.lab8.data.local.UserPreferences
import com.ayerdi.lab8.data.repository.DataStoreAuthRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

// Sofia Lopez - 231929

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val userPreferences = UserPreferences(application)
    private val authRepository = DataStoreAuthRepository(userPreferences)

    val userName: StateFlow<String?> = authRepository.getUserName()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                authRepository.clearUserName()
                onSuccess()
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }
}