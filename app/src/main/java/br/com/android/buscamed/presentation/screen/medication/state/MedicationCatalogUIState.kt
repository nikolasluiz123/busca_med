package br.com.android.buscamed.presentation.screen.medication.state

import br.com.android.buscamed.domain.model.medication.AnvisaMedication
import br.com.android.buscamed.presentation.core.state.dialog.MessageDialogState
import br.com.android.buscamed.presentation.core.state.interfaces.LoadingUIState
import br.com.android.buscamed.presentation.core.state.interfaces.ThrowableUIState

/**
 * Representa o estado da interface da tela de catálogo de medicamentos.
 *
 * @property medications Lista de medicamentos carregados.
 * @property cursor Cursor para paginação.
 * @property hasMoreItems Indica se existem mais itens para carregar.
 * @property showLoading Indica se o carregamento inicial está em andamento.
 * @property messageDialogState Estado para controle de exibição de mensagens de erro.
 * @property onToggleLoading Callback para alternar o estado de carregamento.
 */
data class MedicationCatalogUIState(
    val medications: List<AnvisaMedication> = emptyList(),
    val cursor: Any? = null,
    val hasMoreItems: Boolean = true,
    override val showLoading: Boolean = false,
    override val messageDialogState: MessageDialogState = MessageDialogState(),
    override val onToggleLoading: () -> Unit = { },
) : LoadingUIState, ThrowableUIState