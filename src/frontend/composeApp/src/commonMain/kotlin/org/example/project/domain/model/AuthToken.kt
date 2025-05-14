package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthData(
    val username: String,
    val uuid: String,
    val email: String,
    val token: String
)
