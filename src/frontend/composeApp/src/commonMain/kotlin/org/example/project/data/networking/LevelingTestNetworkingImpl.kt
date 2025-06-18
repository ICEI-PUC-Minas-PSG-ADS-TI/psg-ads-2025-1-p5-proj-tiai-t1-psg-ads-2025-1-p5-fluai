package org.example.project.data.networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.project.domain.model.Email
import org.example.project.domain.model.LevelingTestAnswers
import org.example.project.domain.model.Questions
import org.example.project.domain.model.Response
import org.example.project.domain.service.safeApiCall

class LevelingTestNetworkingImpl(
    private val httpClient: HttpClient
) : LevelingTestNetworking {
    override suspend fun fetchApiQuestions(): Result<List<Questions>> {
        return safeApiCall {
            httpClient.get(urlString = "http://10.0.2.2:5050/lessons/leveling-tests") {
                contentType(ContentType.Application.Json)
            }.body<List<Questions>>()
        }
    }

    override suspend fun submitAnswer(answer : LevelingTestAnswers): Result<Response> {
       return safeApiCall {
           httpClient.post(urlString = "http://10.0.2.2:5050/ollama/define-user-english-level"){
               contentType(ContentType.Application.Json)
               setBody(answer)
           }.body()
       }
    }

    override suspend fun getQuestionSmartChallenges(email: Email): Result<List<Questions>> {
       return safeApiCall {
           httpClient.get(urlString = "http://10.0.2.2:5050/lessons/custom-activity"){
               contentType(ContentType.Application.Json)
               setBody(email)
           }.body()
       }
    }
}