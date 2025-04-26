package org.example.project.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserRequestDto(
    val username : String,
    val password : String,
    val email : String
)
