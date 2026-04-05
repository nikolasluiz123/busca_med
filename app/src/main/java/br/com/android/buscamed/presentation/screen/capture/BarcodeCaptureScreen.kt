package br.com.android.buscamed.presentation.screen.capture

import androidx.camera.core.ImageProxy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.android.buscamed.domain.model.medication.AnvisaMedication
import br.com.android.buscamed.presentation.core.components.dialog.BaseMessageDialog
import br.com.android.buscamed.presentation.screen.capture.components.CameraXPreview
import br.com.android.buscamed.presentation.screen.capture.components.InteractiveOverlay
import br.com.android.buscamed.presentation.screen.capture.state.BarcodeCaptureUIState
import br.com.android.buscamed.presentation.viewmodel.BarcodeCaptureViewModel

/**
 * Componente Stateful para a tela de captura de código de barras.
 *
 * @param viewModel ViewModel que gerencia o estado da captura.
 * @param onBackClick Callback acionado ao clicar no botão de voltar.
 * @param onNavigateToMedicationDetails Callback acionado para navegar para os detalhes de um medicamento único.
 * @param onNavigateToMedicationList Callback acionado para navegar para a lista quando múltiplos medicamentos são encontrados.
 */
@Composable
fun BarcodeCaptureScreen(
    viewModel: BarcodeCaptureViewModel,
    onBackClick: () -> Unit,
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
        onBackClick = onBackClick,
        onFrameAvailable = viewModel::analyzeFrame
    )
}

/**
 * Componente Stateless para a tela de captura de código de barras.
 *
 * @param state Estado atual da interface.
 * @param onBackClick Callback de navegação para retornar à tela anterior.
 * @param onFrameAvailable Callback acionado a cada novo quadro de imagem disponibilizado pela câmera.
 */
@Composable
fun BarcodeCaptureScreen(
    state: BarcodeCaptureUIState,
    onBackClick: () -> Unit,
    onFrameAvailable: (ImageProxy) -> Unit
) {
    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            CameraXPreview(
                modifier = Modifier.fillMaxSize(),
                onAnalyzeFrame = onFrameAvailable
            )

            InteractiveOverlay(
                modifier = Modifier.fillMaxSize(),
                analyzerState = state.analyzerState,
                boundingBox = state.boundingBox,
                sourceDimensions = state.sourceDimensions
            )

            BaseMessageDialog(state = state.messageDialogState)
        }
    }
}