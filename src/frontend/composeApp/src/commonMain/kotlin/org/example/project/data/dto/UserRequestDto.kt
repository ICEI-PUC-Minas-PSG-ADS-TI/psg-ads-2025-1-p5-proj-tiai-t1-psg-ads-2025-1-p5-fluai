package org.example.project.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserRequestDto(
    val name : String,
    val email : String,
    val password : String
)
