package br.com.android.buscamed.presentation.screen.result.state

import br.com.android.buscamed.domain.model.medication.AnvisaMedication
import br.com.android.buscamed.presentation.core.state.dialog.MessageDialogState
import br.com.android.buscamed.presentation.core.state.interfaces.ThrowableUIState

/**
 * Representa o estado da interface da tela de detalhes do medicamento.
 *
 * @property medication Dados do medicamento a ser detalhado.
 * @property messageDialogState Estado para controle de exibição de mensagens de erro.
 */
data class MedicationDetailsUIState(
    val medication: AnvisaMedication? = null,
    override val messageDialogState: MessageDialogState = MessageDialogState()
) : ThrowableUIState