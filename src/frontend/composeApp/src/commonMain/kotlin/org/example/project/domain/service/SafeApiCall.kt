package org.example.project.domain.service

import dev.gitlive.firebase.auth.FirebaseAuthException
import io.ktor.client.network.sockets.ConnectTimeoutException
import kotlinx.io.IOException
import org.example.project.domain.exceptions.HttpException

suspend fun <T> safeApiCall(call: suspend () -> T): Result<T> {
    return try {
        Result.success(call())
    } catch (e: TooManyRequestsException) {
        Result.failure(e)
    } catch (e: FirebaseAuthException) {
        Result.failure(Throwable("Erro na autenticação, verifique seus dados."))
    } catch (e: ConnectTimeoutException) {
        Result.failure(Throwable("Tempo de espera excedido."))
    } catch (e: IOException) {
        Result.failure(Throwable("Sem conexão com a internet."))
    } catch (e: HttpException) {
        Result.failure(Throwable(e.error))
    } catch (e: Exception) {
        Result.failure(Throwable("Erro desconhecido. Tente novamente."))
    }
}


class TooManyRequestsException(message: String) : Exception(message)

