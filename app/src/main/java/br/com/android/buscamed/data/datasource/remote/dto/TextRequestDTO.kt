package br.com.android.buscamed.data.datasource.remote.dto

import kotlinx.serialization.Serializable

/**
 * Objeto de transferência de dados para requisições de processamento de texto.
 *
 * @property text O texto bruto extraído da imagem que será processado pela LLM.
 */
@Serializable
data class TextRequestDTO(val text: String)