package org.example.project.domain.service

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json

internal object KtorApiClient {
    val httpClient = HttpClient {
        install(ContentNegotiation){
            json()
        }

        install(Logging){
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }

    }
}