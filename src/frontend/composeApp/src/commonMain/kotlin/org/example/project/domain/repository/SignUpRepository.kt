package org.example.project.domain.repository

import org.example.project.domain.model.User

interface SignUpRepository {
   suspend fun postUser(user : User)
}