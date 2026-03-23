package br.com.android.buscamed.usecase

import br.com.android.buscamed.domain.core.validation.FieldValidationError
import br.com.android.buscamed.domain.core.validation.GeneralValidationError
import br.com.android.buscamed.domain.core.validation.UseCaseResult
import br.com.android.buscamed.domain.exception.DomainAuthException
import br.com.android.buscamed.domain.model.UserCredentials
import br.com.android.buscamed.domain.service.AuthenticationService
import br.com.android.buscamed.domain.usecase.authentication.DefaultAuthenticationUseCase
import br.com.android.buscamed.domain.usecase.authentication.enumeration.UserCredentialsFieldErrorType
import br.com.android.buscamed.domain.usecase.authentication.enumeration.UserCredentialsFieldValidation
import br.com.android.buscamed.domain.usecase.authentication.enumeration.UserCredentialsGeneralErrorType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Classe de testes unitários para o caso de uso [DefaultAuthenticationUseCase].
 */
class DefaultAuthenticationUseCaseTest {

    private lateinit var authenticationService: AuthenticationService
    private val testScheduler = TestCoroutineScheduler()
    private val dispatcher = StandardTestDispatcher(testScheduler)

    private lateinit var defaultAuthenticationUseCase: DefaultAuthenticationUseCase

    @Before
    fun setUp() {
        authenticationService = mockk()

        defaultAuthenticationUseCase = DefaultAuthenticationUseCase(
            authenticationService = authenticationService,
            dispatcher = dispatcher
        )
    }

    @Test
    fun shouldReturnSuccessWhenCredentialsAreValidAndSignInSucceeds() = runTest(testScheduler) {
        val credentials = UserCredentials("user@example.com", "password123")
        coEvery { authenticationService.signIn(credentials) } returns Unit

        val result = defaultAuthenticationUseCase(credentials)

        assertTrue(result is UseCaseResult.Success)
        coVerify(exactly = 1) { authenticationService.signIn(credentials) }
    }

    @Test
    fun shouldReturnErrorWhenEmailIsEmpty() = runTest(testScheduler) {
        val credentials = UserCredentials("", "password123")

        val result = defaultAuthenticationUseCase(credentials)

        val error = result as UseCaseResult.Error
        val validationError = error.errors.first() as FieldValidationError<*, *>
        assertEquals(UserCredentialsFieldValidation.EMAIL, validationError.field)
        assertEquals(UserCredentialsFieldErrorType.REQUIRED, validationError.type)
        coVerify(exactly = 0) { authenticationService.signIn(any()) }
    }

    @Test
    fun shouldReturnErrorWhenPasswordIsEmpty() = runTest(testScheduler) {
        val credentials = UserCredentials("user@example.com", "")

        val result = defaultAuthenticationUseCase(credentials)

        val error = result as UseCaseResult.Error
        val validationError = error.errors.first() as FieldValidationError<*, *>
        assertEquals(UserCredentialsFieldValidation.PASSWORD, validationError.field)
        assertEquals(UserCredentialsFieldErrorType.REQUIRED, validationError.type)
        coVerify(exactly = 0) { authenticationService.signIn(any()) }
    }

    @Test
    fun shouldReturnMultipleErrorsWhenEmailAndPasswordAreEmpty() = runTest(testScheduler) {
        val credentials = UserCredentials("", "")

        val result = defaultAuthenticationUseCase(credentials)

        val error = result as UseCaseResult.Error
        assertEquals(2, error.errors.size)

        val emailError = error.errors[0] as FieldValidationError<*, *>
        assertEquals(UserCredentialsFieldValidation.EMAIL, emailError.field)
        assertEquals(UserCredentialsFieldErrorType.REQUIRED, emailError.type)

        val passwordError = error.errors[1] as FieldValidationError<*, *>
        assertEquals(UserCredentialsFieldValidation.PASSWORD, passwordError.field)
        assertEquals(UserCredentialsFieldErrorType.REQUIRED, passwordError.type)

        coVerify(exactly = 0) { authenticationService.signIn(any()) }
    }

    @Test
    fun shouldReturnInvalidCredentialsErrorWhenServiceThrowsInvalidCredentials() = runTest(testScheduler) {
        val credentials = UserCredentials("user@example.com", "wrongpassword")
        val exception = DomainAuthException.InvalidCredentials()
        coEvery { authenticationService.signIn(credentials) } throws exception

        val result = defaultAuthenticationUseCase(credentials)

        val error = result as UseCaseResult.Error
        val validationError = error.errors.first() as GeneralValidationError<*>
        assertEquals(UserCredentialsGeneralErrorType.INVALID_CREDENTIALS, validationError.type)
        assertEquals(exception, validationError.cause)
    }

    @Test
    fun shouldReturnNetworkErrorWhenServiceThrowsNetworkError() = runTest(testScheduler) {
        val credentials = UserCredentials("user@example.com", "password123")
        val exception = DomainAuthException.NetworkError()
        coEvery { authenticationService.signIn(credentials) } throws exception

        val result = defaultAuthenticationUseCase(credentials)

        val error = result as UseCaseResult.Error
        val validationError = error.errors.first() as GeneralValidationError<*>
        assertEquals(UserCredentialsGeneralErrorType.NETWORK_ERROR, validationError.type)
        assertEquals(exception, validationError.cause)
    }

    @Test(expected = DomainAuthException.InvalidEmail::class)
    fun shouldPropagateUnmappedDomainAuthException() = runTest(testScheduler) {
        val credentials = UserCredentials("invalid-email", "password123")
        coEvery { authenticationService.signIn(credentials) } throws DomainAuthException.InvalidEmail()

        defaultAuthenticationUseCase(credentials)
    }

    @Test(expected = RuntimeException::class)
    fun shouldPropagateGenericRuntimeException() = runTest(testScheduler) {
        val credentials = UserCredentials("user@example.com", "password123")
        coEvery { authenticationService.signIn(credentials) } throws RuntimeException()

        defaultAuthenticationUseCase(credentials)
    }
}