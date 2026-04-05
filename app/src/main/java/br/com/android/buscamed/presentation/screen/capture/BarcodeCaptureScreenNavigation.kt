package br.com.android.buscamed.presentation.screen.capture

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.android.buscamed.domain.model.medication.AnvisaMedication
import br.com.android.buscamed.presentation.viewmodel.BarcodeCaptureViewModel

const val barcodeCaptureScreenRoute = "barcodeCapture"

fun NavGraphBuilder.barcodeCaptureScreen(
    onBackClick: () -> Unit,
    onNavigateToMedicationDetails: (AnvisaMedication) -> Unit,
    onNavigateToMedicationList: (List<AnvisaMedication>) -> Unit
) {
    composable(route = barcodeCaptureScreenRoute) {
        val barcodeCaptureViewModel = hiltViewModel<BarcodeCaptureViewModel>()

        BarcodeCaptureScreen(
            viewModel = barcodeCaptureViewModel,
            onBackClick = onBackClick,
            onNavigateToMedicationDetails = onNavigateToMedicationDetails,
            onNavigateToMedicationList = onNavigateToMedicationList
        )
    }
}

/**
 * Função de extensão para navegar para a tela de captura de código de barras.
 *
 * @param navOptions Opções adicionais de navegação.
 */
fun NavController.navigateToBarcodeCaptureScreen(navOptions: NavOptions? = null) {
    navigate(route = barcodeCaptureScreenRoute, navOptions = navOptions)
}