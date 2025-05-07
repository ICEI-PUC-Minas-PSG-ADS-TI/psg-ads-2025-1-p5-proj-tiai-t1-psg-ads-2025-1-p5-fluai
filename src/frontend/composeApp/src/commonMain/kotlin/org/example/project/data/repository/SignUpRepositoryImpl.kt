package org.example.project.data.repository

import org.example.project.data.database.local.user.UserLocalDataSource
import org.example.project.data.mapper.toDto
import org.example.project.data.mapper.toEntity
import org.example.project.data.networking.SignUpNetworking

import org.example.project.domain.model.User
import org.example.project.domain.repository.SignUpRepository

class SignUpRepositoryImpl(
    private val local: UserLocalDataSource,
    private val remote: SignUpNetworking
) : SignUpRepository {
    override suspend fun postUser(user: User) : Result<Unit> = remote.postUser(user.toDto()).onSuccess { local.saveUser(user.toEntity()) }
}