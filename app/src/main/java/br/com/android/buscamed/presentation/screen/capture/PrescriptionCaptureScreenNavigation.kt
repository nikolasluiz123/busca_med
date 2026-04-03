package br.com.android.buscamed.presentation.screen.capture

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.android.buscamed.presentation.viewmodel.PrescriptionCaptureViewModel

const val prescriptionCaptureScreenRoute = "prescriptionCapture"

fun NavGraphBuilder.documentCaptureScreen() {
    composable(route = prescriptionCaptureScreenRoute) {
        val viewModel = hiltViewModel<PrescriptionCaptureViewModel>()

        PrescriptionCaptureScreen(
            viewModel = viewModel
        )
    }
}

fun NavController.navigateToPrescriptionCaptureScreen(navOptions: NavOptions? = null) {
    navigate(route = prescriptionCaptureScreenRoute, navOptions = navOptions)
}
