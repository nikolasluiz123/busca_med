package br.com.android.buscamed.presentation.screen.capture

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.android.buscamed.domain.model.pillpack.PillPack
import br.com.android.buscamed.presentation.viewmodel.PillPackCaptureViewModel

const val pillPackCaptureScreenRoute = "pillPackCapture"

fun NavGraphBuilder.pillPackCaptureScreen(
    onPillPackProcessed: (PillPack) -> Unit
) {
    composable(route = pillPackCaptureScreenRoute) {
        val viewModel = hiltViewModel<PillPackCaptureViewModel>()

        PillPackCaptureScreen(
            viewModel = viewModel,
            onPillPackProcessed = onPillPackProcessed
        )
    }
}

fun NavController.navigateToPillPackCaptureScreen(navOptions: NavOptions? = null) {
    navigate(route = pillPackCaptureScreenRoute, navOptions = navOptions)
}