package br.com.android.buscamed.data.datasource.remote.dto

import kotlinx.serialization.Serializable

/**
 * Representa a estrutura de resposta de erro da API.
 *
 * @property errorCode Um código interno de erro para cenários específicos.
 * @property message Uma mensagem descritiva do erro.
 * @property timestamp A data e hora em que o erro ocorreu.
 * @property path O caminho da requisição HTTP.
 * @property traceId O identificador de rastreamento distribuído.
 */
@Serializable
data class ErrorResponseDTO(
    val errorCode: String,
    val message: String,
    val timestamp: String,
    val path: String,
    val traceId: String? = null
)