package org.example.project.domain.service

import dev.gitlive.firebase.auth.FirebaseAuthException
import io.ktor.client.network.sockets.ConnectTimeoutException
import kotlinx.io.IOException
import org.example.project.domain.exceptions.HttpException

suspend fun <T> safeApiCall(call: suspend () -> T): Result<T> {
    return try {
        Result.success(call())
    } catch (e: HttpException) {
        Result.failure(Throwable(e.error))
    }catch (e: FirebaseAuthException){
        Result.failure(Throwable(message = "Erro na autenticação, verifique seus dados."))
    } catch (e: ConnectTimeoutException) {
        Result.failure(Throwable(message = "Tempo de espera excedido."))
    } catch (e: IOException) {
        Result.failure(Throwable(message = "Sem conexão com a internet."))
    } catch (e: Exception) {
        println("ERRO REAL: ${e::class.simpleName} - ${e.message}")
        e.printStackTrace()
        Result.failure(e)
    }
}
