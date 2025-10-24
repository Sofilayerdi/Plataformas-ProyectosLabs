package com.ayerdi.lab8.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// Sofia Lopez - 231929

object HttpClientFactory {

    @Volatile
    private var INSTANCE: HttpClient? = null

    fun create(): HttpClient {
        return INSTANCE ?: synchronized(this) {
            val instance = HttpClient(CIO.create()) {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    })
                }

                install(Logging) {
                    level = LogLevel.ALL
                    logger = Logger.ANDROID
                }

                defaultRequest {
                    url("https://rickandmortyapi.com/api/")
                    contentType(ContentType.Application.Json)
                }
            }
            INSTANCE = instance
            instance
        }
    }
}