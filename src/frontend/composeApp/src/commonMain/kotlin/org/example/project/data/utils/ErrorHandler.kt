package org.example.project.data.utils

sealed class AppError(
    open val userMessage : String,
    val debugMessage : String? = null
) : Throwable(){
    data object NetworkError : AppError("Sem conexão com a internet", "Sem internet")

    data class ApiError(
        val statusCode : Int,
        override val userMessage: String,
        val details : String? = null
    ) : AppError(userMessage, details)
}