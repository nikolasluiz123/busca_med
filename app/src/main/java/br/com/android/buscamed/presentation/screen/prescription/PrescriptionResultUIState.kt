package br.com.android.buscamed.presentation.screen.prescription

import br.com.android.buscamed.domain.model.prescription.Prescription
import br.com.android.buscamed.presentation.core.state.dialog.MessageDialogState

/**
 * Representa o estado da interface da tela de resultados da prescrição.
 *
 * @property prescription O objeto contendo os dados da prescrição processada.
 * @property messageDialogState Estado para controle de exibição de mensagens de erro ou avisos.
 */
data class PrescriptionResultUIState(
    val prescription: Prescription? = null,
    val messageDialogState: MessageDialogState = MessageDialogState()
)