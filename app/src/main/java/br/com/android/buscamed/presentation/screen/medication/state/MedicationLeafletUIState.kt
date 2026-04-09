package br.com.android.buscamed.presentation.screen.medication.state

import br.com.android.buscamed.domain.model.medication.leaflet.PatientLeaflet
import br.com.android.buscamed.domain.model.medication.leaflet.ProfessionalLeaflet
import br.com.android.buscamed.presentation.core.state.dialog.MessageDialogState
import br.com.android.buscamed.presentation.core.state.interfaces.LoadingUIState
import br.com.android.buscamed.presentation.core.state.interfaces.ThrowableUIState

/**
 * Representa o estado da interface da tela de detalhes (bula) do medicamento.
 *
 * @property medicationId Identificador do medicamento.
 * @property patientLeaflet Dados da bula do paciente.
 * @property professionalLeaflet Dados da bula do profissional.
 * @property showLoading Indica se o carregamento está em andamento.
 * @property messageDialogState Estado para controle de exibição de mensagens de erro.
 * @property onToggleLoading Callback para alternar o estado de carregamento.
 */
data class MedicationLeafletUIState(
    val medicationId: String? = null,
    val patientLeaflet: PatientLeaflet? = null,
    val professionalLeaflet: ProfessionalLeaflet? = null,
    override val showLoading: Boolean = false,
    override val messageDialogState: MessageDialogState = MessageDialogState(),
    override val onToggleLoading: () -> Unit = { },
) : LoadingUIState, ThrowableUIState