package org.example.project.domain.usecase

import org.example.project.domain.model.Questions

interface LevelingTestUseCase {
    suspend fun getQuestion(): Result<List<Questions>>
    suspend fun submitAnswer()
}