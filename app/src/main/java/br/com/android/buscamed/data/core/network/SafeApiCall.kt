package br.com.android.buscamed.data.core.network

import br.com.android.buscamed.data.datasource.remote.dto.ErrorResponseDTO
import br.com.android.buscamed.domain.exception.ServiceException
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.SerializationException

/**
 * Envolve uma chamada de rede para tratar erros do Ktor e convertê-los em exceções de domínio.
 *
 * @param T O tipo de retorno esperado da chamada de rede.
 * @param apiCall O bloco de código suspend que executa a requisição HTTP.
 * @return O resultado da requisição em caso de sucesso.
 * @throws ServiceException Quando a API retorna um erro mapeado.
 */
suspend inline fun <T> safeApiCall(crossinline apiCall: suspend () -> T): T {
    return try {
        apiCall()
    } catch (e: ClientRequestException) {
        throw e.response.toServiceException()
    } catch (e: ServerResponseException) {
        throw e.response.toServiceException()
    } catch (e: Exception) {
        throw e
    }
}

/**
 * Converte uma resposta HTTP de erro do Ktor em uma exceção de domínio.
 *
 * @return A exceção de serviço mapeada ou uma exceção genérica caso a conversão falhe.
 */
suspend fun HttpResponse.toServiceException(): ServiceException {
    return try {
        val errorResponse = this.body<ErrorResponseDTO>()
        ServiceException(
            errorCode = errorResponse.errorCode,
            message = errorResponse.message,
            traceId = errorResponse.traceId
        )
    } catch (e: SerializationException) {
        ServiceException(
            errorCode = "SERIALIZATION_ERROR",
            message = "Falha ao processar a resposta de erro do servidor."
        )
    } catch (e: Exception) {
        ServiceException(
            errorCode = "UNKNOWN_ERROR",
            message = "Erro desconhecido ao processar a resposta do servidor."
        )
    }
}