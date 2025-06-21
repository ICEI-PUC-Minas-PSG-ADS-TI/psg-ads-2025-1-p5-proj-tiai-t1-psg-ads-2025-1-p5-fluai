package org.example.project.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ForgotPasswordResponseDto(
    val error: String
)