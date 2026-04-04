package br.com.android.buscamed.domain.model.capture

/**
 * Encapsula os metadados de execução do pipeline de processamento.
 *
 * @property pipelineVersion A versão da implementação atual do algoritmo de leitura.
 */
data class ExecutionMetadata(
    val pipelineVersion: String
)