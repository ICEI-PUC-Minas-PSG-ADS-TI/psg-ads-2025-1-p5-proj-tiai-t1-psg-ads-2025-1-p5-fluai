package org.example.project.data.networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.project.data.dto.UserRequestDto
import org.example.project.domain.service.safeApiCall

class SignUpNetworkingImpl(
    private val httpClient: HttpClient
) : SignUpNetworking {
    override suspend fun postUser(user: UserRequestDto): Result<Unit> {
        return safeApiCall {
            httpClient.post(urlString = "http://10.0.2.2:5050/users/signup") {
                contentType(ContentType.Application.Json)
                setBody(user)
            }.body<Unit>()
        }
    }
}


