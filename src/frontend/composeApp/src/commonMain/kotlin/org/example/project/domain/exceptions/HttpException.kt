package org.example.project.domain.exceptions

import kotlinx.serialization.Serializable

@Serializable
data class HttpException(
    val error : String
) : Exception()