package br.com.android.buscamed.presentation.screen.result

import android.util.Log
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.android.buscamed.data.gson.defaultGSon
import br.com.android.buscamed.domain.model.medication.AnvisaMedication
import br.com.android.buscamed.presentation.viewmodel.MedicationDetailsViewModel
import com.google.gson.GsonBuilder

const val medicationDetailsScreenRoute = "medicationDetailsScreenRoute"
const val medicationDetailsArguments = "medicationDetailsArguments"

fun NavGraphBuilder.medicationDetailsScreen(
    onBackClick: () -> Unit
) {
    composable(route = "$medicationDetailsScreenRoute?$medicationDetailsArguments={$medicationDetailsArguments}") {
        val viewModel = hiltViewModel<MedicationDetailsViewModel>()

        MedicationDetailsScreen(
            viewModel = viewModel,
            onBackClick = onBackClick
        )
    }
}

fun NavController.navigateToMedicationDetails(args: MedicationDetailsArgs, navOptions: NavOptions? = null) {
    val json = GsonBuilder().defaultGSon().toJson(args)
    navigate(route = "${medicationDetailsScreenRoute}?${medicationDetailsArguments}={$json}", navOptions = navOptions)
}

class MedicationDetailsArgs(val medication: AnvisaMedication)