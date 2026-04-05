package br.com.android.buscamed.domain.usecase.medication.enumeration

/**
 * Enumera os tipos de erros gerais que podem ocorrer durante a validação e busca de um código de barras.
 */
enum class BarcodeGeneralErrorType {
    INVALID_BARCODE,
    MEDICATION_NOT_FOUND,
    NETWORK_ERROR,
    UNKNOWN_ERROR
}