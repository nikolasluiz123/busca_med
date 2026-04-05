package br.com.android.buscamed.presentation.screen.home

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.android.buscamed.presentation.viewmodel.HomeViewModel

const val homeScreenRoute = "home"

fun NavGraphBuilder.homeScreen(
    onNavigateToPrescriptionCapture: () -> Unit,
    onNavigateToPillPackCapture: () -> Unit,
    onNavigateToBarcodeCapture: () -> Unit
) {
    composable(route = homeScreenRoute) {
        val homeViewModel = hiltViewModel<HomeViewModel>()

        HomeScreen(
            viewModel = homeViewModel,
            onNavigateToPrescriptionCapture = onNavigateToPrescriptionCapture,
            onNavigateToPillPackCapture = onNavigateToPillPackCapture,
            onNavigateToBarcodeCapture = onNavigateToBarcodeCapture
        )
    }
}

fun NavController.navigateToHomeScreen(navOptions: NavOptions? = null) {
    navigate(route = homeScreenRoute, navOptions = navOptions)
}
