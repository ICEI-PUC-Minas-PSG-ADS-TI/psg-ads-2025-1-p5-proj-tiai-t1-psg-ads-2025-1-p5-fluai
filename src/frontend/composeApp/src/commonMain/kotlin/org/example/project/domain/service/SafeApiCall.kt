package org.example.project.domain.service

import dev.gitlive.firebase.auth.FirebaseAuthException
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.ClientRequestException
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
    }catch (e : ClientRequestException){
        Result.failure(e)
    } catch (e: Exception) {
        Result.failure(Throwable(message = "Erro desconhecido. Tente novamente."))
    }
}
