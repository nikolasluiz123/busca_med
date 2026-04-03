package br.com.android.buscamed.presentation.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.android.buscamed.R
import br.com.android.buscamed.presentation.core.components.buttons.BaseButton
import br.com.android.buscamed.presentation.core.components.topbar.SimpleTopAppBar
import br.com.android.buscamed.presentation.state.HomeUIState
import br.com.android.buscamed.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToDocumentCapture: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onNavigateToDocumentCapture = onNavigateToDocumentCapture
    )
}

@Composable
fun HomeScreen(
    state: HomeUIState = HomeUIState(),
    onNavigateToDocumentCapture: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = stringResource(R.string.home_screen_title),
                showNavigationIcon = false,
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BaseButton(
                label = stringResource(R.string.home_screen_label_button_document_capture),
                onClickListener = onNavigateToDocumentCapture
            )
        }
    }
}
