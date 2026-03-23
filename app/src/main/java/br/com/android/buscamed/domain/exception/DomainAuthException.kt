package br.com.android.buscamed.domain.exception

sealed class DomainAuthException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    class InvalidCredentials(cause: Throwable? = null) : DomainAuthException(cause = cause)
    class NetworkError(cause: Throwable? = null) : DomainAuthException(cause = cause)
    class InvalidEmail(cause: Throwable? = null) : DomainAuthException(cause = cause)
    class EmailAlreadyInUse(cause: Throwable? = null) : DomainAuthException(cause = cause)
}