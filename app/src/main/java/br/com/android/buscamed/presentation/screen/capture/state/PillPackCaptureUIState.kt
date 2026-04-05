package br.com.android.buscamed.presentation.screen.capture.state

import br.com.android.buscamed.domain.model.capture.AnalyzerState
import br.com.android.buscamed.domain.model.capture.BoundingBox
import br.com.android.buscamed.domain.model.capture.ImageDimension
import br.com.android.buscamed.domain.model.pillpack.PillPack
import br.com.android.buscamed.presentation.core.state.dialog.MessageDialogState
import br.com.android.buscamed.presentation.core.state.interfaces.ThrowableUIState

/**
 * Representa o estado da interface de captura de cartelas de comprimidos.
 *
 * @property analyzerState Estado atual do analisador de quadros.
 * @property textBoundingBox Caixa delimitadora do texto detectado.
 * @property sourceDimensions Dimensões da imagem de origem.
 * @property isCaptureButtonEnabled Indica se o botão de captura pode ser acionado.
 * @property isCapturing Indica se a captura e o processamento estão em andamento.
 * @property pillPack Contém o resultado do processamento da cartela, caso exista.
 * @property messageDialogState Estado para controle de exibição de mensagens de erro.
 */
data class PillPackCaptureUIState(
    val analyzerState: AnalyzerState = AnalyzerState.NOT_DETECTED,
    val textBoundingBox: BoundingBox? = null,
    val sourceDimensions: ImageDimension? = null,
    val isCaptureButtonEnabled: Boolean = false,
    val isCapturing: Boolean = false,
    val pillPack: PillPack? = null,
    override val messageDialogState: MessageDialogState = MessageDialogState()
): ThrowableUIState