package org.example.project.data.networking

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.project.data.dto.UserRequestDto
import org.example.project.domain.error.DataError
import org.example.project.domain.error.Result
import org.example.project.domain.error.mapExceptionToDataError

class SignUpNetworkingImpl(
    private val httpClient: HttpClient
) : SignUpNetworking {
    override suspend fun postUser(user: UserRequestDto) : Result<Unit, DataError>{
        return try{
            val response = httpClient.post(urlString = "http://192.168.1.4:5050/users/signup") {
                contentType(ContentType.Application.Json)
                setBody(user)
            }

            when(response.status.value){
                in 200..299 -> Result.Success(Unit)
                in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
                else -> Result.Error(DataError.Network.UNKNOWN)
            }
        } catch (e: Exception){
            Result.Error(mapExceptionToDataError(e))
        }

    }
}

