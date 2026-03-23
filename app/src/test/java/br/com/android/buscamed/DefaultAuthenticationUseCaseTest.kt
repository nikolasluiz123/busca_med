package br.com.android.buscamed

import br.com.android.buscamed.domain.service.AuthenticationService
import br.com.android.buscamed.domain.usecase.authentication.DefaultAuthenticationUseCase
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before

/**
 * Classe de testes unitários para [DefaultAuthenticationUseCase].
 */
class DefaultAuthenticationUseCaseTest {

    private lateinit var authenticationService: AuthenticationService
    private val dispatcher = StandardTestDispatcher()

    private lateinit var defaultAuthenticationUseCase: DefaultAuthenticationUseCase

    @Before
    fun setUp() {
        authenticationService = mockk()

        defaultAuthenticationUseCase = DefaultAuthenticationUseCase(
            authenticationService = authenticationService,
            dispatcher = dispatcher
        )
    }
}