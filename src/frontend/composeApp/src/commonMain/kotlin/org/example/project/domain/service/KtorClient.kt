package org.example.project.domain.service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.project.domain.exceptions.HttpException

internal object KtorApiClient {

    fun getClient(database: String) : HttpClient{

        val httpClient = HttpClient {
            install(ContentNegotiation){
                json()
                json(Json {
                    ignoreUnknownKeys = true
                })
            }

            install(Logging){
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 300000L
                connectTimeoutMillis = 30000L
                socketTimeoutMillis = 300000L
            }


            HttpResponseValidator {
                validateResponse { response ->
                    if (response.status == io.ktor.http.HttpStatusCode.TooManyRequests) {
                        throw TooManyRequestsException("Atividades em geração")
                    }
                    else if (response.status.value > 299) {
                        val error = runCatching { response.body<HttpException>() }.getOrNull()
                        throw error ?: HttpException("Erro desconhecido.")
                    }
                }
            }
        }
        return httpClient
    }
}