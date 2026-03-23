package br.com.android.buscamed.domain.usecase.registeruser

import br.com.android.buscamed.domain.core.validation.FieldValidationError
import br.com.android.buscamed.domain.core.validation.GeneralValidationError
import br.com.android.buscamed.domain.core.validation.UseCaseResult
import br.com.android.buscamed.domain.core.validation.ValidationError
import br.com.android.buscamed.domain.exception.DomainAuthException
import br.com.android.buscamed.domain.model.User
import br.com.android.buscamed.domain.model.UserCredentials
import br.com.android.buscamed.domain.repository.UserRepository
import br.com.android.buscamed.domain.service.AuthenticationService
import br.com.android.buscamed.domain.usecase.registeruser.enumeration.UserField
import br.com.android.buscamed.domain.usecase.registeruser.enumeration.UserFieldErrorType
import br.com.android.buscamed.domain.usecase.registeruser.enumeration.UserGeneralErrorType
import br.com.android.buscamed.injection.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authenticationService: AuthenticationService,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()

    suspend operator fun invoke(user: User): UseCaseResult<Unit> = withContext(dispatcher) {
        val validationErrors = mutableListOf<ValidationError>()

        val name = user.name.trim()

        if (name.isEmpty()) {
            validationErrors.add(
                FieldValidationError(
                    field = UserField.NAME,
                    type = UserFieldErrorType.REQUIRED
                )
            )
        } else if (name.length > UserField.NAME.maxLength) {
            validationErrors.add(
                FieldValidationError(
                    field = UserField.NAME,
                    type = UserFieldErrorType.TOO_LONG
                )
            )
        }

        val email = user.email.trim()
        if (email.isEmpty()) {
            validationErrors.add(
                FieldValidationError(
                    field = UserField.EMAIL,
                    type = UserFieldErrorType.REQUIRED
                )
            )
        } else if (email.length > UserField.EMAIL.maxLength) {
            validationErrors.add(
                FieldValidationError(
                    field = UserField.EMAIL,
                    type = UserFieldErrorType.TOO_LONG
                )
            )
        } else if (!emailRegex.matches(email)) {
            validationErrors.add(
                FieldValidationError(
                    field = UserField.EMAIL,
                    type = UserFieldErrorType.INVALID_FORMAT
                )
            )
        }

        val password = user.password?.trim() ?: ""
        if (password.isEmpty()) {
            validationErrors.add(
                FieldValidationError(
                    field = UserField.PASSWORD,
                    type = UserFieldErrorType.REQUIRED
                )
            )
        } else if (password.length < 6) {
            validationErrors.add(
                FieldValidationError(
                    field = UserField.PASSWORD,
                    type = UserFieldErrorType.TOO_SHORT
                )
            )
        } else if (password.length > UserField.PASSWORD.maxLength) {
            validationErrors.add(
                FieldValidationError(
                    field = UserField.PASSWORD,
                    type = UserFieldErrorType.TOO_LONG
                )
            )
        }

        if (validationErrors.isNotEmpty()) {
            return@withContext UseCaseResult.Error(validationErrors)
        }

        try {
            val userUid = authenticationService.signUp(UserCredentials(email, password))

            val userToSave = user.copy(
                id = userUid,
                normalizedName = name.lowercase(),
                password = null
            )

            userRepository.save(userToSave)
            return@withContext UseCaseResult.Success()
        } catch (e: DomainAuthException) {
            when (e) {
                is DomainAuthException.EmailAlreadyInUse -> {
                    validationErrors.add(GeneralValidationError(UserGeneralErrorType.EMAIL_ALREADY_IN_USE, e))
                }

                is DomainAuthException.NetworkError -> {
                    validationErrors.add(GeneralValidationError(UserGeneralErrorType.NETWORK_ERROR, e))
                }

                else -> throw e
            }
        }

        return@withContext UseCaseResult.Error(validationErrors)
    }
}