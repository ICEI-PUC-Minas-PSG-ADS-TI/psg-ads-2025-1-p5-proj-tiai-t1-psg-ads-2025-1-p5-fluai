package org.example.project.data.repository

import org.example.project.data.networking.HomeNetworking
import org.example.project.domain.model.Email
import org.example.project.domain.model.Response
import org.example.project.domain.repository.HomeRepository

class HomeRepositoryImpl(
    val homeNetworking: HomeNetworking
) : HomeRepository {
    override suspend fun verifyLevelingTest(email: Email): Result<Response?>  {
        return homeNetworking.verifyLevelingTest(email)
    }

    override suspend fun preloadQuestions(email: Email): Result<Unit> = homeNetworking.preloadQuestions(email)
}