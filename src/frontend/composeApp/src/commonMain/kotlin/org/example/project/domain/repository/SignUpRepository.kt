package org.example.project.domain.repository

import org.example.project.domain.error.DataError
import org.example.project.domain.error.Result
import org.example.project.domain.model.User

interface SignUpRepository {
   suspend fun postUser(user : User) : Result<Unit, DataError>
}