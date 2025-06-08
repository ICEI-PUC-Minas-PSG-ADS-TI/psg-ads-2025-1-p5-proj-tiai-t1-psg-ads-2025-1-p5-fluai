package org.example.project.domain.usecase

import org.example.project.domain.model.Questions
import org.example.project.domain.repository.LevelingTestRepository

class LevelingTestUseCaseImpl(
    private val levelingTestRepository: LevelingTestRepository
): LevelingTestUseCase {
    override suspend fun getQuestion() : Result<List<Questions>> = levelingTestRepository.getQuestion()
    override suspend fun submitAnswer() {
        TODO("Not yet implemented")
    }
}