package org.example.project.domain.usecase

import org.example.project.domain.model.Email
import org.example.project.domain.repository.HomeRepository

class HomeUseCaseImpl(
    val homeRepository : HomeRepository
) : HomeUseCase {
    override suspend fun verifyLevelingTest(email : Email) : Result<Boolean> =  homeRepository.verifyLevelingTest(email)
}