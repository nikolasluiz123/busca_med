package br.com.android.buscamed.presentation.screen.pillpack

import br.com.android.buscamed.domain.model.pillpack.PillPack
import br.com.android.buscamed.presentation.core.state.dialog.MessageDialogState

/**
 * Representa o estado da interface da tela de resultados da cartela de comprimidos.
 *
 * @property pillPack O objeto contendo os dados da cartela processada.
 * @property messageDialogState Estado para controle de exibição de mensagens de erro ou avisos.
 */
data class PillPackResultUIState(
    val pillPack: PillPack? = null,
    val messageDialogState: MessageDialogState = MessageDialogState()
)