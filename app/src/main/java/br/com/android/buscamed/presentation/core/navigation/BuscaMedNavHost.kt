package br.com.android.buscamed.presentation.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import br.com.android.buscamed.presentation.screen.login.loginScreen
import br.com.android.buscamed.presentation.screen.login.loginScreenRoute
import br.com.android.buscamed.presentation.screen.registeruser.RegisterUserScreenArgs
import br.com.android.buscamed.presentation.screen.registeruser.navigateToRegisterUserScreen
import br.com.android.buscamed.presentation.screen.registeruser.registerUserScreen

/**
 * Host de navegação principal do aplicativo BuscaMed.
 *
 * Esta função configura o gráfico de navegação do Compose, definindo as rotas,
 * as telas correspondentes e as transições entre elas.
 *
 * @param modifier Modificador de layout para o contêiner de navegação.
 * @param navController Controlador responsável por gerenciar a pilha de telas.
 */
@Composable
fun BuscaMedNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = loginScreenRoute,
        modifier = modifier
    ) {
        loginScreen(
            onNavigateToHome = {

            },
            onNavigateToRegister = {
                navController.navigateToRegisterUserScreen(args = RegisterUserScreenArgs())
            }
        )

        registerUserScreen(
            onBackClick = navController::popBackStack,
            onSaveSuccess = {

            }
        )
    }
}
