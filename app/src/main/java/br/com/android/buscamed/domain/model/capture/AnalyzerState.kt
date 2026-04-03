package br.com.android.buscamed.domain.model.capture

/**
 * Define os possíveis estados de alinhamento de um documento durante a análise de quadros.
 */
enum class AnalyzerState {
    NO_DOCUMENT,
    PARTIAL,
    ALIGNED
}