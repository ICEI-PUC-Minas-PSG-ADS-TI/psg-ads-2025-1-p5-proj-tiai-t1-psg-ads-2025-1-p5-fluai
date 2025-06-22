package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LevelingTestAnswers(
    val email : String,
    val questions : List<String>
)
