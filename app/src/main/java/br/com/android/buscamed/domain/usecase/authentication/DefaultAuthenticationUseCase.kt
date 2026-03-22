package br.com.android.buscamed.domain.usecase.authentication

import br.com.android.buscamed.domain.core.validation.FieldValidationError
import br.com.android.buscamed.domain.core.validation.GeneralValidationError
import br.com.android.buscamed.domain.core.validation.UseCaseResult
import br.com.android.buscamed.domain.core.validation.ValidationError
import br.com.android.buscamed.domain.model.UserCredentials
import br.com.android.buscamed.domain.usecase.authentication.enumeration.UserCredentialsFieldErrorType
import br.com.android.buscamed.domain.usecase.authentication.enumeration.UserCredentialsFieldValidation
import br.com.android.buscamed.domain.usecase.authentication.enumeration.UserCredentialsGeneralErrorType
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class DefaultAuthenticationUseCase(
    private val firebaseAuth: FirebaseAuth
) {
    suspend operator fun invoke(credentials: UserCredentials): UseCaseResult<Unit> = withContext(Dispatchers.IO) {
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
            firebaseAuth.signInWithEmailAndPassword(credentials.email, credentials.password).await()
            return@withContext UseCaseResult.Success()
        } catch (e: FirebaseAuthException) {
            val errorType = when (e.errorCode) {
                "ERROR_INVALID_CREDENTIAL" -> UserCredentialsGeneralErrorType.INVALID_CREDENTIALS
                "ERROR_USER_DISABLED" -> UserCredentialsGeneralErrorType.ACCOUNT_BLOCKED
                else -> UserCredentialsGeneralErrorType.UNKNOWN_ERROR
            }

            validationErrors.add(GeneralValidationError(errorType))
        } catch (_: FirebaseNetworkException) {
            validationErrors.add(GeneralValidationError(UserCredentialsGeneralErrorType.NETWORK_ERROR))
        } catch (_: Exception) {
            validationErrors.add(GeneralValidationError(UserCredentialsGeneralErrorType.UNKNOWN_ERROR))
        }

        return@withContext UseCaseResult.Error(validationErrors)
    }
}
