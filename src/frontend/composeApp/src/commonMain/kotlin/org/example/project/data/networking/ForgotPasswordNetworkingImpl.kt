package org.example.project.data.networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.project.data.dto.Email
import org.example.project.data.dto.ForgotPasswordResponseDto
import org.example.project.domain.service.safeApiCall

class ForgotPasswordNetworkingImpl(
    private val httpClient: HttpClient
) : ForgotPasswordNetworking {
    override suspend fun sendForgotPasswordRequest(request: Email): Result<ForgotPasswordResponseDto> {
        return safeApiCall {
            httpClient.post("http://192.168.100.48:5050/users/recover-password") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()
        }
    }
}