package org.example.project.data.repository

import org.example.project.data.networking.LevelingTestNetworking
import org.example.project.domain.model.Email
import org.example.project.domain.model.LevelingTestAnswers
import org.example.project.domain.model.Questions
import org.example.project.domain.model.Response
import org.example.project.domain.repository.LevelingTestRepository

class LevelingTestRepositoryImpl(
    private var levelingTestNetworking: LevelingTestNetworking
) : LevelingTestRepository {
    override suspend fun getQuestion() : Result<List<Questions>> = levelingTestNetworking.fetchApiQuestions()
    override suspend fun submitAnswer(answer : LevelingTestAnswers): Result<Response> = levelingTestNetworking.submitAnswer(answer)
    override suspend fun getQuestionSmartChallenges(email: Email): Result<List<Questions>> = levelingTestNetworking.getQuestionSmartChallenges(email)
}