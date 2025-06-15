package org.example.project.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Questions(
    val id : Int,
    var question : String,
    var answer : String,
    var options : String,
    val level : String,
    val description: String
){
    fun optionList() : List<String>{
        return try {
            Json.decodeFromString(options)
        }catch (e: Exception){
            emptyList<String>()
        }
    }
}
