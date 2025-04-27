package org.example.project.data.networking

import org.example.project.data.dto.UserRequestDto
import org.example.project.domain.error.DataError
import org.example.project.domain.error.Result

interface SignUpNetworking {
    suspend fun postUser(user : UserRequestDto) : Result<Unit, DataError>
}