package br.com.android.buscamed.presentation.screen.result.state

import br.com.android.buscamed.domain.model.medication.AnvisaMedication
import br.com.android.buscamed.presentation.core.state.dialog.MessageDialogState
import br.com.android.buscamed.presentation.core.state.interfaces.ThrowableUIState

/**
 * Representa o estado da interface da tela de listagem de medicamentos.
 *
 * @property medications Lista de medicamentos a ser exibida.
 * @property messageDialogState Estado para controle de exibição de mensagens de erro.
 */
data class MedicationListUIState(
    val medications: List<AnvisaMedication> = emptyList(),
    override val messageDialogState: MessageDialogState = MessageDialogState()
) : ThrowableUIState