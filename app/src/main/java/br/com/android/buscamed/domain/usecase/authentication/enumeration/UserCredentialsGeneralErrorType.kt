package br.com.android.buscamed.domain.usecase.authentication.enumeration

enum class UserCredentialsGeneralErrorType {
    INVALID_CREDENTIALS,
    ACCOUNT_BLOCKED,
    NETWORK_ERROR,
    UNKNOWN_ERROR
}