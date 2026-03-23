package br.com.android.buscamed.presentation.core.state.interfaces.dialog

import br.com.android.buscamed.presentation.core.state.enumeration.EnumDialogType

/**
 * Interface funcional para o callback de exibição de diálogos de mensagem.
 *
 * Define o contrato para funções que configuram e disparam a exibição de alertas
 * na interface do usuário, abstraindo a lógica de renderização do diálogo.
 */
fun interface ShowMessageDialogCallback {
    /**
     * Configura e exibe o diálogo de mensagem.
     *
     * @param type O tipo visual e semântico do diálogo (Erro, Confirmação, Informação).
     * @param message O texto da mensagem a ser exibida no corpo do diálogo.
     * @param onConfirm Função callback executada ao clicar no botão de confirmação ou OK.
     * @param onCancel Função callback executada ao cancelar ou fechar o diálogo.
     */
    fun onShow(type: EnumDialogType, message: String, onConfirm: () -> Unit, onCancel: () -> Unit)
}
