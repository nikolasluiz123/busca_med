package br.com.android.buscamed.presentation.screen.capture

import androidx.camera.core.ImageProxy
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.android.buscamed.domain.model.medication.AnvisaMedication
import br.com.android.buscamed.presentation.core.components.dialog.BaseMessageDialog
import br.com.android.buscamed.presentation.screen.capture.components.CameraXPreview
import br.com.android.buscamed.presentation.screen.capture.components.InteractiveOverlay
import br.com.android.buscamed.presentation.screen.capture.state.BarcodeCaptureUIState
import br.com.android.buscamed.presentation.viewmodel.BarcodeCaptureViewModel

@Composable
fun BarcodeCaptureScreen(
    viewModel: BarcodeCaptureViewModel,
    onNavigateToMedicationDetails: (AnvisaMedication) -> Unit,
    onNavigateToMedicationList: (List<AnvisaMedication>) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.searchResult) {
        state.searchResult?.let { medications ->
            if (medications.size == 1) {
                onNavigateToMedicationDetails(medications.first())
            } else if (medications.size > 1) {
                onNavigateToMedicationList(medications)
            }
        }
    }

    BarcodeCaptureScreen(
        state = state,
        onFrameAvailable = viewModel::analyzeFrame
    )
}

@Composable
fun BarcodeCaptureScreen(
    state: BarcodeCaptureUIState,
    onFrameAvailable: (ImageProxy) -> Unit
) {
    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.Black)
        ) {
            CameraXPreview(
                modifier = Modifier.fillMaxSize(),
                onAnalyzeFrame = onFrameAvailable
            )

            if (!state.isSearching) {
                InteractiveOverlay(
                    modifier = Modifier.fillMaxSize(),
                    analyzerState = state.analyzerState,
                    boundingBox = state.boundingBox,
                    sourceDimensions = state.sourceDimensions
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }
            }

            BaseMessageDialog(state = state.messageDialogState)
        }
    }
}