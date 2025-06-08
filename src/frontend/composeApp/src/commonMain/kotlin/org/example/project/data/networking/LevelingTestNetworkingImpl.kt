package org.example.project.data.networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.project.domain.model.Questions
import org.example.project.domain.service.safeApiCall

class LevelingTestNetworkingImpl(
    private val httpClient: HttpClient
) : LevelingTestNetworking {
    override suspend fun fetchApiQuestions(): Result<List<Questions>> {
        return safeApiCall {
            httpClient.get(urlString = "http://127.0.0.1:5050/generate-level-test") {
                contentType(ContentType.Application.Json)
            }.body<List<Questions>>()
        }
    }

    override suspend fun submitAnswer() {
        TODO("Not yet implemented")
    }
}