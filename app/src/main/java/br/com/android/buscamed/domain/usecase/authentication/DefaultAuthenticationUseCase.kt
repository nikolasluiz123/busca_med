package br.com.android.buscamed.domain.usecase.authentication

import br.com.android.buscamed.domain.core.validation.FieldValidationError
import br.com.android.buscamed.domain.core.validation.GeneralValidationError
import br.com.android.buscamed.domain.core.validation.UseCaseResult
import br.com.android.buscamed.domain.core.validation.ValidationError
import br.com.android.buscamed.domain.exception.DomainAuthException
import br.com.android.buscamed.domain.model.UserCredentials
import br.com.android.buscamed.domain.service.AuthenticationService
import br.com.android.buscamed.domain.usecase.authentication.enumeration.UserCredentialsFieldErrorType
import br.com.android.buscamed.domain.usecase.authentication.enumeration.UserCredentialsFieldValidation
import br.com.android.buscamed.domain.usecase.authentication.enumeration.UserCredentialsGeneralErrorType
import br.com.android.buscamed.injection.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Caso de uso responsável por realizar a autenticação do usuário no sistema.
 *
 * Esta classe orquestra a validação das credenciais de entrada e a integração
 * com o serviço de autenticação, retornando um resultado padronizado.
 *
 * @property authenticationService Serviço de autenticação injetado.
 * @property dispatcher O dispatcher de corrotina utilizado para garantir que a lógica seja executada em uma thread de IO.
 */
class DefaultAuthenticationUseCase @Inject constructor(
    private val authenticationService: AuthenticationService,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    /**
     * Executa a lógica de autenticação.
     *
     * Valida os campos obrigatórios (e-mail e senha) e tenta realizar o login.
     * Caso ocorram erros de validação ou falhas na autenticação, a função retorna
     * uma lista de [ValidationError].
     *
     * @param credentials Objeto contendo o e-mail e a senha informados pelo usuário.
     * @return Um [UseCaseResult] indicando o sucesso da operação ou os erros encontrados.
     */
    suspend operator fun invoke(credentials: UserCredentials): UseCaseResult<Unit> = withContext(dispatcher) {
        val validationErrors = mutableListOf<ValidationError>()

        if (credentials.email.trim().isEmpty()) {
            validationErrors.add(
                FieldValidationError(
                    field = UserCredentialsFieldValidation.EMAIL,
                    type = UserCredentialsFieldErrorType.REQUIRED
                )
            )
        }

        if (credentials.password.trim().isEmpty()) {
            validationErrors.add(
                FieldValidationError(
                    field = UserCredentialsFieldValidation.PASSWORD,
                    type = UserCredentialsFieldErrorType.REQUIRED
                )
            )
        }

        if (validationErrors.isNotEmpty()) {
            return@withContext UseCaseResult.Error(validationErrors)
        }

        try {
            authenticationService.signIn(credentials)
            return@withContext UseCaseResult.Success()
        } catch (e: DomainAuthException) {
            val errorType = when (e) {
                is DomainAuthException.InvalidCredentials -> UserCredentialsGeneralErrorType.INVALID_CREDENTIALS
                is DomainAuthException.NetworkError -> UserCredentialsGeneralErrorType.NETWORK_ERROR
                else -> throw e
            }

            validationErrors.add(GeneralValidationError(errorType, e))
        }

        return@withContext UseCaseResult.Error(validationErrors)
    }
}
