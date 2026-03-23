package br.com.android.buscamed

import br.com.android.buscamed.domain.repository.UserRepository
import br.com.android.buscamed.domain.service.AuthenticationService
import br.com.android.buscamed.domain.usecase.registeruser.RegisterUserUseCase
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before

/**
 * Classe de testes unitários para [RegisterUserUseCase].
 */
class RegisterUserUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var authenticationService: AuthenticationService
    private val dispatcher = StandardTestDispatcher()

    private lateinit var registerUserUseCase: RegisterUserUseCase

    @Before
    fun setUp() {
        userRepository = mockk()
        authenticationService = mockk()

        registerUserUseCase = RegisterUserUseCase(
            userRepository = userRepository,
            authenticationService = authenticationService,
            dispatcher = dispatcher
        )
    }
}