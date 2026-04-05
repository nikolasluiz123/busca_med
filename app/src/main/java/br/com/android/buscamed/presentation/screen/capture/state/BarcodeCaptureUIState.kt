package br.com.android.buscamed.presentation.screen.capture.state

import br.com.android.buscamed.domain.model.capture.AnalyzerState
import br.com.android.buscamed.domain.model.capture.BoundingBox
import br.com.android.buscamed.domain.model.capture.ImageDimension
import br.com.android.buscamed.domain.model.medication.AnvisaMedication
import br.com.android.buscamed.presentation.core.state.dialog.MessageDialogState
import br.com.android.buscamed.presentation.core.state.interfaces.LoadingUIState
import br.com.android.buscamed.presentation.core.state.interfaces.ThrowableUIState

/**
 * Representa o estado da tela de captura de código de barras.
 *
 * @property title Título exibido na barra superior.
 * @property isScanning Indica se a câmera está ativamente processando quadros.
 * @property isSearching Indica se está a ocorrer uma busca na base de dados pelo código lido.
 * @property analyzerState Estado atual do analisador em relação ao alinhamento do código de barras.
 * @property boundingBox Caixa delimitadora do código de barras no quadro atual, se detetado.
 * @property sourceDimensions Dimensões do quadro original processado.
 * @property capturedBarcode O valor numérico em texto do código de barras capturado.
 * @property searchResult Lista de medicamentos encontrados após a busca.
 * @property messageDialogState Estado do diálogo de mensagens para apresentação de erros.
 * @property showLoading Indica se um indicador de carregamento bloqueante deve ser exibido.
 * @property onToggleLoading Callback para alternar o estado de carregamento.
 */
data class BarcodeCaptureUIState(
    val isScanning: Boolean = true,
    val isSearching: Boolean = false,
    val analyzerState: AnalyzerState = AnalyzerState.NOT_DETECTED,
    val boundingBox: BoundingBox? = null,
    val sourceDimensions: ImageDimension? = null,
    val capturedBarcode: String? = null,
    val searchResult: List<AnvisaMedication>? = null,
    override val messageDialogState: MessageDialogState = MessageDialogState(),
) : ThrowableUIState