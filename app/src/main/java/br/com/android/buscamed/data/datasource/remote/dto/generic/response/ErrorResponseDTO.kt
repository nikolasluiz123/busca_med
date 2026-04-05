package br.com.android.buscamed.data.datasource.remote.dto.generic.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseDTO(
    val errorCode: String,
    val message: String,
    val timestamp: String,
    val path: String,
    val traceId: String? = null
)