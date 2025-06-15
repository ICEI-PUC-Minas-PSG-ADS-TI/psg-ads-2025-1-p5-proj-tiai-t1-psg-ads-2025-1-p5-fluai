package org.example.project.data.repository

import org.example.project.data.networking.LevelingTestNetworking
import org.example.project.domain.model.LevelingTestAnswers
import org.example.project.domain.model.Questions
import org.example.project.domain.repository.LevelingTestRepository

class LevelingTestRepositoryImpl(
    private var levelingTestNetworking: LevelingTestNetworking
) : LevelingTestRepository {
    override suspend fun getQuestion() : Result<List<Questions>> = levelingTestNetworking.fetchApiQuestions()
    override suspend fun submitAnswer(answer : LevelingTestAnswers): Result<String> = levelingTestNetworking.submitAnswer(answer)
}