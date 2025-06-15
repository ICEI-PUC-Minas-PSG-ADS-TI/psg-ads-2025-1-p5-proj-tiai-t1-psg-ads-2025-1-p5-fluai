package org.example.project.data.networking

import org.example.project.domain.model.LevelingTestAnswers
import org.example.project.domain.model.Questions
import org.example.project.domain.model.Response

interface LevelingTestNetworking {
    suspend fun fetchApiQuestions() : Result<List<Questions>>
    suspend fun submitAnswer(answer : LevelingTestAnswers) : Result<Response>
}