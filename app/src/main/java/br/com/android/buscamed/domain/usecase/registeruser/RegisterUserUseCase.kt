package br.com.android.buscamed.domain.usecase.registeruser

import br.com.android.buscamed.domain.core.validation.FieldValidationError
import br.com.android.buscamed.domain.core.validation.GeneralValidationError
import br.com.android.buscamed.domain.core.validation.UseCaseResult
import br.com.android.buscamed.domain.core.validation.ValidationError
import br.com.android.buscamed.domain.model.User
import br.com.android.buscamed.domain.repository.UserRepository
import br.com.android.buscamed.domain.usecase.registeruser.enumeration.UserField
import br.com.android.buscamed.domain.usecase.registeruser.enumeration.UserFieldErrorType
import br.com.android.buscamed.domain.usecase.registeruser.enumeration.UserGeneralErrorType
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RegisterUserUseCase(
    private val userRepository: UserRepository,
    private val firebaseAuth: FirebaseAuth
) {
    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()

    suspend operator fun invoke(user: User): UseCaseResult<Unit> = withContext(Dispatchers.IO) {
        val validationErrors = mutableListOf<ValidationError>()

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
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user

            if (firebaseUser != null) {
                userRepository.save(user.copy(id = firebaseUser.uid, password = null))
                return@withContext UseCaseResult.Success()
            } else {
                validationErrors.add(GeneralValidationError(UserGeneralErrorType.UNKNOWN_ERROR))
            }
        } catch (_: FirebaseAuthUserCollisionException) {
            validationErrors.add(GeneralValidationError(UserGeneralErrorType.EMAIL_ALREADY_IN_USE))
        } catch (_: FirebaseNetworkException) {
            validationErrors.add(GeneralValidationError(UserGeneralErrorType.NETWORK_ERROR))
        } catch (_: FirebaseAuthException) {
            validationErrors.add(GeneralValidationError(UserGeneralErrorType.UNKNOWN_ERROR))
        } catch (_: Exception) {
            validationErrors.add(GeneralValidationError(UserGeneralErrorType.UNKNOWN_ERROR))
        }

        return@withContext UseCaseResult.Error(validationErrors)
    }
}
