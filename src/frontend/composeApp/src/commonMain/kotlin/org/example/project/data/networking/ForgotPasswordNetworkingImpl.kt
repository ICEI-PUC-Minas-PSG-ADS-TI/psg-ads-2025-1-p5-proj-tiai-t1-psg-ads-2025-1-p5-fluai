package org.example.project.data.networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.project.data.dto.ForgotPasswordRequestDto
import org.example.project.data.dto.ForgotPasswordResponseDto
import org.example.project.domain.service.safeApiCall

class ForgotPasswordNetworkingImpl(
    private val httpClient: HttpClient
) : ForgotPasswordNetworking {
    override suspend fun sendForgotPasswordRequest(request: ForgotPasswordRequestDto): Result<String> {
        return safeApiCall {
            httpClient.post("http://10.0.2.2:5050/users/forgot-password") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body<ForgotPasswordResponseDto>().resetLink
        }
    }
}