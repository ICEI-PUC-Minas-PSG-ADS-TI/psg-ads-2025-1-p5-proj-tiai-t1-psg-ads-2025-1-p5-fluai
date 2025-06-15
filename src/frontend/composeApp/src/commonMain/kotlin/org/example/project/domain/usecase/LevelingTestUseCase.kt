package org.example.project.domain.usecase

import org.example.project.domain.model.LevelingTestAnswers
import org.example.project.domain.model.Questions
import org.example.project.domain.model.Response

interface LevelingTestUseCase {
    suspend fun getQuestion(): Result<List<Questions>>
    suspend fun submitAnswer(answer : LevelingTestAnswers) : Result<Response>
}