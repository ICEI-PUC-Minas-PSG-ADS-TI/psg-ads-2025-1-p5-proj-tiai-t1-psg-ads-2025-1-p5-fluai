package org.example.project.data.networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import org.example.project.domain.model.Email

class HomeNetworkingImpl(
   val httpClient: HttpClient
) : HomeNetworking {
    override suspend fun verifyLevelingTest(email: Email): Result<Boolean> {
       return try  {
           println(email)
           val response : String = httpClient.get(urlString = "http://10.0.2.2:5050/users/verify-leveling-test"){
               contentType(ContentType.Application.Json)
               setBody(email)
           }.body()

           Result.success(true)
        }catch(e : ClientRequestException){
            if (e.response.status == HttpStatusCode.NotFound){
                Result.success(false)
            }else{
                Result.failure(e)
            }
       }catch (e : Exception){
           Result.failure(e)
       }
    }
}