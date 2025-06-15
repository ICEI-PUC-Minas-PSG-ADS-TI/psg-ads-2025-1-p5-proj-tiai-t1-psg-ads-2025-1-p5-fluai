package org.example.project.data.repository

import org.example.project.data.networking.HomeNetworking
import org.example.project.domain.model.Email
import org.example.project.domain.repository.HomeRepository

class HomeRepositoryImpl(
    val homeNetworking: HomeNetworking
) : HomeRepository {
    override suspend fun verifyLevelingTest(email: Email): Result<Boolean> = homeNetworking.verifyLevelingTest(email)

}