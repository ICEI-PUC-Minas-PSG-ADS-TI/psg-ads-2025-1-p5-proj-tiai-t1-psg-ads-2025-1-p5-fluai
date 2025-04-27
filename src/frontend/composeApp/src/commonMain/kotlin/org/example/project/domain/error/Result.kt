package org.example.project.domain.error

import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException


typealias RootError = Error
sealed interface Result<out D, out E : RootError> {
    data class Success<out D, out E : RootError>(val data : D) : Result<D, E>
    data class Error<out D, out E: RootError>(val error : E): Result<D, E>
}

fun mapExceptionToDataError(exception: Exception) : DataError{
    return when(exception){
        is ConnectTimeoutException,
        is SocketTimeoutException,
        is TimeoutCancellationException -> DataError.Network.REQUEST_TIMEOUT
        is IOException -> DataError.Network.NO_INTERNET
        is SerializationException -> DataError.Network.SERIALIZATION_ERROR
        is ClassCastException -> DataError.Network.UNKNOWN
        is ServerResponseException -> DataError.Network.SERVER_ERROR
        else -> DataError.Network.UNKNOWN
    }
}

fun mapErrorToFriendlyMessage(error: DataError): String {
    return when (error) {
        DataError.Network.REQUEST_TIMEOUT -> "Tempo de conexão esgotado. Verifique sua internet."
        DataError.Network.NO_INTERNET -> "Sem conexão com a internet."
        DataError.Network.SERVER_ERROR -> "Erro no servidor. Tente novamente mais tarde."
        DataError.Network.SERIALIZATION_ERROR -> "Erro ao processar os dados."
        DataError.Network.UNKNOWN -> "Erro desconhecido. Tente novamente."
    }
}