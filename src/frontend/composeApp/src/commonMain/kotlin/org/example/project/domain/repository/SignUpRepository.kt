package org.example.project.domain.repository

import frontend.composeapp.generated.resources.Res
import org.example.project.domain.model.User

interface SignUpRepository {
   suspend fun postUser(user : User) : Result<Unit>
}