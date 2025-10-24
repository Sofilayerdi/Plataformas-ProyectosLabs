package com.ayerdi.lab8.data.repository

import kotlinx.coroutines.flow.Flow

// Sofia Lopez - 231929


interface AuthRepository {
    suspend fun saveUserName(name: String)
    suspend fun clearUserName()
    fun getUserName(): Flow<String?>
}

class DataStoreAuthRepository(
    private val userPreferences: com.ayerdi.lab8.data.local.UserPreferences
) : AuthRepository {
    override suspend fun saveUserName(name: String) {
        userPreferences.saveUserName(name)
    }

    override suspend fun clearUserName() {
        userPreferences.clearUserName()
    }

    override fun getUserName(): Flow<String?> {
        return userPreferences.userName
    }
}