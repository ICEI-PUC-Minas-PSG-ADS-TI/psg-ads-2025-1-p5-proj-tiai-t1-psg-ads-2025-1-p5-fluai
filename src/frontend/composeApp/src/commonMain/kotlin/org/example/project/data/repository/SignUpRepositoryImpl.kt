package org.example.project.data.repository

import org.example.project.data.mapper.toDto
import org.example.project.data.networking.SignUpNetworking
import org.example.project.data.utils.AppError
import org.example.project.domain.model.User
import org.example.project.domain.repository.SignUpRepository

class SignUpRepositoryImpl(
   private val signUpNetworking: SignUpNetworking
) : SignUpRepository {
    override suspend fun postUser(user: User) {
        try {
            val dto = user.toDto()
            signUpNetworking.postUser(dto)
        } catch (e: AppError) {
            throw e
        } catch (e: Exception) {
            throw AppError.ApiError(-1, "Falha na operação: ${e.message}")
        }
    }
}