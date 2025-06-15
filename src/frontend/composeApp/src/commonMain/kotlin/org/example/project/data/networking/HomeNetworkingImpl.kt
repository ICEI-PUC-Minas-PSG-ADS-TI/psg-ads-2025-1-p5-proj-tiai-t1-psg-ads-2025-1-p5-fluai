package org.example.project.data.networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import org.example.project.domain.model.Email
import org.example.project.domain.service.safeApiCall

class HomeNetworkingImpl(
   val httpClient: HttpClient
) : HomeNetworking {
    override suspend fun verifyLevelingTest(email: Email): Result<Boolean> {
        return safeApiCall {
            httpClient.post("http://10.0.2.2:5050/users/verify-leveling-test") {
                contentType(ContentType.Application.Json)
                setBody(email)
            }.body()
        }
    }
}