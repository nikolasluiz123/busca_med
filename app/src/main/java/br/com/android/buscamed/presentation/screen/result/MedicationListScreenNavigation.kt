package br.com.android.buscamed.presentation.screen.result

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.android.buscamed.data.gson.defaultGSon
import br.com.android.buscamed.domain.model.medication.AnvisaMedication
import br.com.android.buscamed.presentation.screen.medication.MedicationListScreen
import br.com.android.buscamed.presentation.viewmodel.MedicationListViewModel
import com.google.gson.GsonBuilder

const val medicationListScreenRoute = "medicationListScreenRoute"
const val medicationListScreenArguments = "medicationListScreenArguments"

fun NavGraphBuilder.medicationListScreen(
    onBackClick: () -> Unit,
    onMedicationClick: (MedicationDetailsArgs) -> Unit
) {
    composable(route = "$medicationListScreenRoute?$medicationListScreenArguments={$medicationListScreenArguments}") {
        val viewModel = hiltViewModel<MedicationListViewModel>()

        MedicationListScreen(
            viewModel = viewModel,
            onBackClick = onBackClick,
            onMedicationClick = onMedicationClick
        )
    }
}

fun NavController.navigateToMedicationList(
    args: MedicationListArgs,
    navOptions: NavOptions? = null
) {
    val json = GsonBuilder().defaultGSon().toJson(args)
    navigate(route = "${medicationListScreenRoute}?${medicationListScreenArguments}={$json}", navOptions = navOptions)
}

class MedicationListArgs(val medications: List<AnvisaMedication>)
