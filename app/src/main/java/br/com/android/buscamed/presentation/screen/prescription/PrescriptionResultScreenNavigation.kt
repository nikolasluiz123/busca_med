package br.com.android.buscamed.presentation.screen.prescription

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.android.buscamed.data.gson.defaultGSon
import br.com.android.buscamed.domain.model.prescription.Prescription
import br.com.android.buscamed.presentation.viewmodel.PrescriptionResultViewModel
import com.google.gson.GsonBuilder

const val prescriptionResultScreen = "prescriptionResult"
const val prescriptionResultArguments = "prescriptionResultArguments"

fun NavGraphBuilder.prescriptionResultScreen(onBackClick: () -> Unit) {
    composable(route = "$prescriptionResultScreen?$prescriptionResultArguments={$prescriptionResultArguments}") {
        val viewModel = viewModel<PrescriptionResultViewModel>()

        PrescriptionResultScreen(
            viewModel = viewModel,
            onBackClick = onBackClick
        )
    }
}

fun NavController.navigateToPrescriptionResult(args: PrescriptionResultScreenArgs, navOptions: NavOptions? = null) {
    val json = GsonBuilder().defaultGSon().toJson(args)
    navigate(route = "${prescriptionResultScreen}?${prescriptionResultArguments}={$json}", navOptions = navOptions)
}

data class PrescriptionResultScreenArgs(
    val prescription: Prescription
)