package br.com.android.buscamed.presentation.screen.medication

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.android.buscamed.data.gson.defaultGSon
import br.com.android.buscamed.presentation.viewmodel.MedicationLeafletViewModel
import com.google.gson.GsonBuilder

const val medicationLeafletScreenRoute = "medicationLeafletScreenRoute"
const val medicationLeafletArguments = "medicationLeafletArguments"

fun NavGraphBuilder.medicationLeafletScreen(
    onBackClick: () -> Unit
) {
    composable(route = "$medicationLeafletScreenRoute?$medicationLeafletArguments={$medicationLeafletArguments}") {
        val viewModel = hiltViewModel<MedicationLeafletViewModel>()

        MedicationLeafletScreen(
            viewModel = viewModel,
            onBackClick = onBackClick
        )
    }
}

fun NavController.navigateToMedicationLeaflet(args: MedicationLeafletScreenArgs, navOptions: NavOptions? = null) {
    val json = GsonBuilder().defaultGSon().toJson(args)
    navigate(route = "${medicationLeafletScreenRoute}?${medicationLeafletArguments}={$json}", navOptions = navOptions)
}

class MedicationLeafletScreenArgs(val medicationId: String)