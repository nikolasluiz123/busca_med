package br.com.android.buscamed.domain.model.capture

/**
 * Representa o resultado completo da extração de texto de uma imagem.
 *
 * @property text O texto completo extraído, formatado com as quebras de linha originais.
 * @property blocks Lista de blocos de texto estruturados contidos na imagem.
 */
data class TextResult(
    val text: String,
    val blocks: List<TextBlock>
)