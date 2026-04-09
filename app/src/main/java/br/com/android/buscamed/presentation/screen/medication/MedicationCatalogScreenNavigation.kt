package br.com.android.buscamed.presentation.screen.medication

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.android.buscamed.presentation.viewmodel.MedicationCatalogViewModel

const val medicationCatalogScreenRoute = "medicationCatalogScreenRoute"

fun NavGraphBuilder.medicationCatalogScreen(
    onBackClick: () -> Unit,
    onMedicationClick: (MedicationLeafletScreenArgs) -> Unit
) {
    composable(route = medicationCatalogScreenRoute) {
        val viewModel = hiltViewModel<MedicationCatalogViewModel>()

        MedicationCatalogScreen(
            viewModel = viewModel,
            onBackClick = onBackClick,
            onMedicationClick = onMedicationClick
        )
    }
}

fun NavController.navigateToMedicationCatalog(navOptions: NavOptions? = null) {
    navigate(route = medicationCatalogScreenRoute, navOptions = navOptions)
}