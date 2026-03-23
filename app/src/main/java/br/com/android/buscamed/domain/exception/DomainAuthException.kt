package br.com.android.buscamed.domain.exception

/**
 * Classe base selada para exceções de autenticação no domínio.
 *
 * Centraliza as falhas que podem ocorrer durante processos de login ou cadastro,
 * permitindo que a camada de apresentação trate erros de forma tipada e segura.
 *
 * @param message Mensagem descritiva do erro.
 * @param cause A exceção original (causa raiz).
 */
sealed class DomainAuthException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {

    /**
     * Lançada quando o e-mail ou a senha informados estão incorretos.
     * 
     * Mapeada a partir de erros de usuário inexistente ou credenciais inválidas do Firebase.
     */
    class InvalidCredentials(cause: Throwable? = null) : DomainAuthException(cause = cause)

    /**
     * Lançada quando ocorre uma falha de conectividade com a internet.
     * 
     * Impede que a operação de autenticação seja concluída por problemas de rede.
     */
    class NetworkError(cause: Throwable? = null) : DomainAuthException(cause = cause)

    /**
     * Lançada quando o formato do e-mail é considerado inválido pelo provedor de autenticação.
     */
    class InvalidEmail(cause: Throwable? = null) : DomainAuthException(cause = cause)

    /**
     * Lançada durante o cadastro quando o e-mail informado já está vinculado a outra conta.
     */
    class EmailAlreadyInUse(cause: Throwable? = null) : DomainAuthException(cause = cause)
}
