package br.com.android.buscamed.data.core.network

import br.com.android.buscamed.data.datasource.remote.dto.ErrorResponseDTO
import br.com.android.buscamed.domain.exception.ServiceException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

/**
 * Executa uma chamada de rede de forma segura, validando o status HTTP e
 * realizando a desserialização adequada com base no resultado.
 *
 * @param T O tipo de objeto esperado em caso de sucesso (HTTP 2xx).
 * @param apiCall O bloco de código suspend que executa a requisição HTTP e retorna um [HttpResponse].
 * @return O objeto do tipo [T] desserializado em caso de sucesso.
 * @throws ServiceException Quando a API retorna um erro mapeado ou ocorre uma falha de conexão/serialização.
 */
suspend inline fun <reified T> safeApiCall(crossinline apiCall: suspend () -> HttpResponse): T {
    val response = try {
        apiCall()
    } catch (e: Exception) {
        throw ServiceException(
            errorCode = "NETWORK_ERROR",
            message = "Não foi possível conectar ao servidor. Verifique sua conexão.",
            traceId = null
        )
    }

    return if (response.status.isSuccess()) {
        try {
            response.body<T>()
        } catch (e: Exception) {
            throw ServiceException(
                errorCode = "SERIALIZATION_ERROR",
                message = "Falha ao processar a resposta do servidor.",
                traceId = null
            )
        }
    } else {
        throw response.toServiceException()
    }
}

/**
 * Converte uma resposta HTTP de erro do Ktor em uma exceção de domínio [ServiceException].
 *
 * @return A exceção de serviço mapeada com os dados retornados pela API ou um erro genérico.
 */
suspend fun HttpResponse.toServiceException(): ServiceException {
    return try {
        val errorResponse = this.body<ErrorResponseDTO>()
        ServiceException(
            errorCode = errorResponse.errorCode,
            message = errorResponse.message,
            traceId = errorResponse.traceId
        )
    } catch (e: Exception) {
        ServiceException(
            errorCode = "UNKNOWN_API_ERROR",
            message = "Erro desconhecido ao processar a requisição. Status: ${this.status.value}",
            traceId = null
        )
    }
}