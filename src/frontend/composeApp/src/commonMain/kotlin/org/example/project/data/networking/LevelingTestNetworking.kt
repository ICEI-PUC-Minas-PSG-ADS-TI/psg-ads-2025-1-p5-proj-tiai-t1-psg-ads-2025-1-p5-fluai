package org.example.project.data.networking

import org.example.project.domain.model.Questions

interface LevelingTestNetworking {
    suspend fun fetchApiQuestions() : Result<List<Questions>>
    suspend fun submitAnswer()
}