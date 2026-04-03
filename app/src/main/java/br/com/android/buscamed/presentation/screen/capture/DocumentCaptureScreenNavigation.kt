package br.com.android.buscamed.presentation.screen.capture

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.android.buscamed.presentation.screen.home.HomeScreen
import br.com.android.buscamed.presentation.screen.home.homeScreenRoute
import br.com.android.buscamed.presentation.viewmodel.CameraCaptureViewModel
import br.com.android.buscamed.presentation.viewmodel.HomeViewModel

const val documentCaptureScreenRoute = "documentCapture"

fun NavGraphBuilder.documentCaptureScreen() {
    composable(route = documentCaptureScreenRoute) {
        val homeViewModel = hiltViewModel<CameraCaptureViewModel>()

        DocumentCaptureScreen(
            viewModel = homeViewModel
        )
    }
}

fun NavController.navigateToDocumentCaptureScreen(navOptions: NavOptions? = null) {
    navigate(route = documentCaptureScreenRoute, navOptions = navOptions)
}
