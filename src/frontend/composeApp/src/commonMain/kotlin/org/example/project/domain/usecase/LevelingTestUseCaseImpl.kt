package org.example.project.domain.usecase

import org.example.project.domain.model.LevelingTestAnswers
import org.example.project.domain.model.Questions
import org.example.project.domain.model.Response
import org.example.project.domain.repository.LevelingTestRepository

class LevelingTestUseCaseImpl(
    private val levelingTestRepository: LevelingTestRepository
): LevelingTestUseCase {
    override suspend fun getQuestion() : Result<List<Questions>> = levelingTestRepository.getQuestion()
    override suspend fun submitAnswer(answer : LevelingTestAnswers): Result<Response> = levelingTestRepository.submitAnswer(answer)
}