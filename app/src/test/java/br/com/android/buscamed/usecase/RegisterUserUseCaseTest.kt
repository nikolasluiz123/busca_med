package br.com.android.buscamed.usecase

import br.com.android.buscamed.domain.core.validation.FieldValidationError
import br.com.android.buscamed.domain.core.validation.GeneralValidationError
import br.com.android.buscamed.domain.core.validation.UseCaseResult
import br.com.android.buscamed.domain.exception.DomainAuthException
import br.com.android.buscamed.domain.model.User
import br.com.android.buscamed.domain.model.UserCredentials
import br.com.android.buscamed.domain.repository.UserRepository
import br.com.android.buscamed.domain.service.AuthenticationService
import br.com.android.buscamed.domain.usecase.registeruser.RegisterUserUseCase
import br.com.android.buscamed.domain.usecase.registeruser.enumeration.UserField
import br.com.android.buscamed.domain.usecase.registeruser.enumeration.UserFieldErrorType
import br.com.android.buscamed.domain.usecase.registeruser.enumeration.UserGeneralErrorType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Classe de testes unitários para o caso de uso [RegisterUserUseCase].
 */
class RegisterUserUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var authenticationService: AuthenticationService
    private val testScheduler = TestCoroutineScheduler()
    private val dispatcher = StandardTestDispatcher(testScheduler)

    private lateinit var registerUserUseCase: RegisterUserUseCase

    @Before
    fun setUp() {
        userRepository = mockk(relaxed = true)
        authenticationService = mockk()

        registerUserUseCase = RegisterUserUseCase(
            userRepository = userRepository,
            authenticationService = authenticationService,
            dispatcher = dispatcher
        )
    }

    @Test
    fun shouldReturnErrorWhenNameIsEmpty() = runTest(testScheduler) {
        val user = User(null, "", "test@example.com", "password123")

        val result = registerUserUseCase(user)

        val error = result as UseCaseResult.Error
        val validationError = error.errors.first() as FieldValidationError<*, *>
        assertEquals(UserField.NAME, validationError.field)
        assertEquals(UserFieldErrorType.REQUIRED, validationError.type)
    }

    @Test
    fun shouldReturnErrorWhenNameIsTooLong() = runTest(testScheduler) {
        val longName = "a".repeat(257)
        val user = User(null, longName, "test@example.com", "password123")

        val result = registerUserUseCase(user)

        val error = result as UseCaseResult.Error
        val validationError = error.errors.first() as FieldValidationError<*, *>
        assertEquals(UserField.NAME, validationError.field)
        assertEquals(UserFieldErrorType.TOO_LONG, validationError.type)
    }

    @Test
    fun shouldReturnErrorWhenEmailIsEmpty() = runTest(testScheduler) {
        val user = User(null, "John Doe", "", "password123")

        val result = registerUserUseCase(user)

        val error = result as UseCaseResult.Error
        val validationError = error.errors.first() as FieldValidationError<*, *>
        assertEquals(UserField.EMAIL, validationError.field)
        assertEquals(UserFieldErrorType.REQUIRED, validationError.type)
    }

    @Test
    fun shouldReturnErrorWhenEmailIsTooLong() = runTest(testScheduler) {
        val longEmail = "a".repeat(250) + "@test.com"
        val user = User(null, "John Doe", longEmail, "password123")

        val result = registerUserUseCase(user)

        val error = result as UseCaseResult.Error
        val validationError = error.errors.first() as FieldValidationError<*, *>
        assertEquals(UserField.EMAIL, validationError.field)
        assertEquals(UserFieldErrorType.TOO_LONG, validationError.type)
    }

    @Test
    fun shouldReturnErrorWhenEmailFormatIsInvalid() = runTest(testScheduler) {
        val user = User(null, "John Doe", "invalid-email", "password123")

        val result = registerUserUseCase(user)

        val error = result as UseCaseResult.Error
        val validationError = error.errors.first() as FieldValidationError<*, *>
        assertEquals(UserField.EMAIL, validationError.field)
        assertEquals(UserFieldErrorType.INVALID_FORMAT, validationError.type)
    }

    @Test
    fun shouldReturnErrorWhenPasswordIsEmpty() = runTest(testScheduler) {
        val user = User(null, "John Doe", "test@example.com", "")

        val result = registerUserUseCase(user)

        val error = result as UseCaseResult.Error
        val validationError = error.errors.first() as FieldValidationError<*, *>
        assertEquals(UserField.PASSWORD, validationError.field)
        assertEquals(UserFieldErrorType.REQUIRED, validationError.type)
    }

    @Test
    fun shouldReturnErrorWhenPasswordIsTooShort() = runTest(testScheduler) {
        val user = User(null, "John Doe", "test@example.com", "12345")

        val result = registerUserUseCase(user)

        val error = result as UseCaseResult.Error
        val validationError = error.errors.first() as FieldValidationError<*, *>
        assertEquals(UserField.PASSWORD, validationError.field)
        assertEquals(UserFieldErrorType.TOO_SHORT, validationError.type)
    }

    @Test
    fun shouldReturnErrorWhenPasswordIsTooLong() = runTest(testScheduler) {
        val longPassword = "a".repeat(4097)
        val user = User(null, "John Doe", "test@example.com", longPassword)

        val result = registerUserUseCase(user)

        val error = result as UseCaseResult.Error
        val validationError = error.errors.first() as FieldValidationError<*, *>
        assertEquals(UserField.PASSWORD, validationError.field)
        assertEquals(UserFieldErrorType.TOO_LONG, validationError.type)
    }

    @Test
    fun shouldReturnMultipleErrorsWhenAllFieldsAreInvalid() = runTest(testScheduler) {
        val user = User(null, "", "invalid-email", "123")

        val result = registerUserUseCase(user)

        val error = result as UseCaseResult.Error
        assertEquals(3, error.errors.size)
    }

    @Test
    fun shouldReturnSuccessAndSaveUserCorrectly() = runTest(testScheduler) {
        val userUid = "uid123"
        val user = User(null, "John Doe", "test@example.com", "password123")
        val credentials = UserCredentials(user.email, user.password!!)
        val userSlot = slot<User>()

        coEvery { authenticationService.signUp(credentials) } returns userUid

        val result = registerUserUseCase(user)

        assertTrue(result is UseCaseResult.Success)
        coVerify { userRepository.save(capture(userSlot)) }
        assertEquals(userUid, userSlot.captured.id)
        assertEquals("john doe", userSlot.captured.normalizedName)
        assertNull(userSlot.captured.password)
        assertEquals(user.name, userSlot.captured.name)
        assertEquals(user.email, userSlot.captured.email)
    }

    @Test
    fun shouldReturnEmailAlreadyInUseErrorWhenServiceThrowsException() = runTest(testScheduler) {
        val user = User(null, "John Doe", "test@example.com", "password123")
        val exception = DomainAuthException.EmailAlreadyInUse()
        coEvery { authenticationService.signUp(any()) } throws exception

        val result = registerUserUseCase(user)

        val error = result as UseCaseResult.Error
        val validationError = error.errors.first() as GeneralValidationError<*>
        assertEquals(UserGeneralErrorType.EMAIL_ALREADY_IN_USE, validationError.type)
        assertEquals(exception, validationError.cause)
    }

    @Test
    fun shouldReturnNetworkErrorWhenServiceThrowsException() = runTest(testScheduler) {
        val user = User(null, "John Doe", "test@example.com", "password123")
        val exception = DomainAuthException.NetworkError()
        coEvery { authenticationService.signUp(any()) } throws exception

        val result = registerUserUseCase(user)

        val error = result as UseCaseResult.Error
        val validationError = error.errors.first() as GeneralValidationError<*>
        assertEquals(UserGeneralErrorType.NETWORK_ERROR, validationError.type)
        assertEquals(exception, validationError.cause)
    }

    @Test(expected = RuntimeException::class)
    fun shouldPropagateGenericRuntimeExceptionFromService() = runTest(testScheduler) {
        val user = User(null, "John Doe", "test@example.com", "password123")
        coEvery { authenticationService.signUp(any()) } throws RuntimeException()

        registerUserUseCase(user)
    }

    @Test(expected = RuntimeException::class)
    fun shouldPropagateGenericRuntimeExceptionFromRepository() = runTest(testScheduler) {
        val user = User(null, "John Doe", "test@example.com", "password123")
        coEvery { authenticationService.signUp(any()) } returns "uid123"
        coEvery { userRepository.save(any()) } throws RuntimeException()

        registerUserUseCase(user)
    }
}
