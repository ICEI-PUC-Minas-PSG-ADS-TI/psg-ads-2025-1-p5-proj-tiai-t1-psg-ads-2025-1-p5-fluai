package org.example.project.data.networking

import org.example.project.data.dto.UserRequestDto

interface SignUpNetworking {
    suspend fun postUser(user : UserRequestDto) : Result<Unit>
}