package org.example.project.domain.model

data class Questions(
    var question : String,
    var answer : String,
    var options : List<String>
)
