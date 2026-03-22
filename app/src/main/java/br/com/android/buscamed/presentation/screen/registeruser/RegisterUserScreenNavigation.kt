package br.com.android.buscamed.presentation.screen.registeruser

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.android.buscamed.data.gson.defaultGSon
import br.com.android.buscamed.presentation.viewmodel.RegisterUserViewModel
import com.google.gson.GsonBuilder

const val registerUserScreenRoute = "registerUser"
const val registerUserArguments = "registerUserArguments"

fun NavGraphBuilder.registerUserScreen(
    onBackClick: () -> Unit,
    onSaveSuccess: () -> Unit
) {
    composable(route = "$registerUserScreenRoute?$registerUserArguments={$registerUserArguments}") {
        val registerUserViewModel = hiltViewModel<RegisterUserViewModel>()

        RegisterUserScreen(
            viewModel = registerUserViewModel,
            onBackClick = onBackClick,
            onSaveSuccess = onSaveSuccess
        )
    }
}

fun NavController.navigateToRegisterUserScreen(args: RegisterUserScreenArgs, navOptions: NavOptions? = null) {
    val json = GsonBuilder().defaultGSon().toJson(args)
    navigate(route = "${registerUserScreenRoute}?${registerUserArguments}={$json}", navOptions = navOptions)
}

class RegisterUserScreenArgs(
    val userId: String? = null
)