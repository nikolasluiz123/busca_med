package br.com.android.buscamed.presentation.screen.pillpack

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.android.buscamed.data.gson.defaultGSon
import br.com.android.buscamed.domain.model.pillpack.PillPack
import br.com.android.buscamed.presentation.viewmodel.PillPackResultViewModel
import com.google.gson.GsonBuilder

const val pillPackResultScreenRoute = "pillPackResult"
const val pillPackResultArguments = "pillPackResultArguments"

fun NavGraphBuilder.pillPackResultScreen(onBackClick: () -> Unit) {
    composable(route = "$pillPackResultScreenRoute?$pillPackResultArguments={$pillPackResultArguments}") {
        val viewModel = hiltViewModel<PillPackResultViewModel>()

        PillPackResultScreen(
            viewModel = viewModel,
            onBackClick = onBackClick
        )
    }
}

fun NavController.navigateToPillPackResult(args: PillPackResultScreenArgs, navOptions: NavOptions? = null) {
    val json = GsonBuilder().defaultGSon().toJson(args)
    navigate(route = "${pillPackResultScreenRoute}?${pillPackResultArguments}={$json}", navOptions = navOptions)
}


/**
 * Argumentos para a tela de resultados da cartela de comprimidos.
 *
 * @property pillPack O objeto contendo os dados da cartela processada.
 */
data class PillPackResultScreenArgs(
    val pillPack: PillPack
)
