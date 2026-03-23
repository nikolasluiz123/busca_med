package br.com.android.buscamed.presentation.core.components.dialog

import br.com.android.buscamed.presentation.core.state.enumeration.EnumDialogType
import br.com.android.buscamed.presentation.core.state.interfaces.dialog.ShowMessageDialogCallback

/**
 * Exibe um diálogo de erro utilizando o callback de mensagem.
 *
 * @receiver O callback responsável pela exibição do diálogo.
 * @param message Mensagem de erro a ser exibida no corpo do diálogo.
 */
fun ShowMessageDialogCallback.showErrorDialog(message: String) {
    this.onShow(
        type = EnumDialogType.ERROR,
        message = message,
        onConfirm = { },
        onCancel = { }
    )
}

/**
 * Exibe um diálogo de confirmação com ação personalizada.
 *
 * @receiver O callback responsável pela exibição do diálogo.
 * @param message Mensagem de confirmação para o usuário.
 * @param onConfirm Função callback executada ao clicar no botão de confirmação.
 */
fun ShowMessageDialogCallback.showConfirmationDialog(message: String, onConfirm: () -> Unit) {
    this.onShow(
        type = EnumDialogType.CONFIRMATION,
        message = message,
        onConfirm = onConfirm,
        onCancel = { }
    )
}

/**
 * Exibe um diálogo informativo.
 *
 * @receiver O callback responsável pela exibição do diálogo.
 * @param message Mensagem informativa a ser exibida.
 */
fun ShowMessageDialogCallback.showInformationDialog(message: String) {
    this.onShow(
        type = EnumDialogType.INFORMATION,
        message = message,
        onConfirm = { },
        onCancel = { }
    )
}
