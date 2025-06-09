package org.example.project.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ForgotPasswordRequestDto(
    val email: String
)
