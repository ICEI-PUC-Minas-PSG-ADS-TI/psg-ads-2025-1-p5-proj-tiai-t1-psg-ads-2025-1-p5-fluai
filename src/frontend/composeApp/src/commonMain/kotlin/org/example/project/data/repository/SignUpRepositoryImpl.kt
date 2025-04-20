package org.example.project.data.repository

import org.example.project.data.mapper.toDto
import org.example.project.data.networking.SignUpNetworking
import org.example.project.domain.model.User
import org.example.project.domain.repository.SignUpRepository

class SignUpRepositoryImpl(
   private val signUpNetworking: SignUpNetworking
) : SignUpRepository {
    override suspend fun postUser(user: User) {
        val dto = user.toDto()
        signUpNetworking.postUser(dto)
    }
}