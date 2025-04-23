package org.example.project.data.networking

import frontend.composeapp.generated.resources.Res
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.io.IOException
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import org.example.project.data.dto.UserRequestDto
import org.example.project.data.utils.AppError

class SignUpNetworkingImpl(
    private val httpClient : HttpClient
) : SignUpNetworking {
    override suspend fun postUser(user: UserRequestDto) : Result<Unit>  {
        return try {
            val response = httpClient.post(urlString = "http://10.0.2.2:5050/users/signup"){
                contentType(ContentType.Application.Json)
                setBody(user)
            }

            when(response.status.value){
                in 400..499 -> {
                    val errorBody = response.body<ErrorResponse>()
                    Result.failure(
                        AppError.ApiError(
                            statusCode = errorBody.code,
                            userMessage = errorBody.message
                        )
                    )
                }
                500 -> Result.failure(AppError.ApiError(500, "Erro Inesperado"))

                else -> Result.success(Unit)
            }
        }catch (e : IOException){
            Result.failure(e)
        }catch (e: SerializationException){
            Result.failure(AppError.ApiError(-1, "Falha na comunicação: ${e.message ?: "Erro desconhecido"}"))
        }

    }
}

@Serializable
data class ErrorResponse(
    val code : Int,
    val message : String
)