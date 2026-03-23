package br.com.android.buscamed.presentation.core.state.dialog

import br.com.android.buscamed.presentation.core.state.enumeration.EnumDialogType
import br.com.android.buscamed.presentation.core.state.interfaces.dialog.ShowMessageDialogCallback

/**
 * Representa o estado completo de um diálogo de mensagem na interface.
 *
 * Encapsula as propriedades necessárias para configurar e exibir alertas,
 * mensagens de erro ou confirmações para o usuário.
 *
 * @property dialogType O tipo visual do diálogo (Erro, Alerta, Sucesso).
 * @property dialogMessage O texto descritivo a ser exibido.
 * @property showDialog Controla se o diálogo deve ser renderizado na tela.
 * @property onShowDialog Função callback para configurar e disparar a exibição do diálogo.
 * @property onHideDialog Função callback para fechar o diálogo.
 * @property onConfirm Função callback executada ao confirmar a ação do diálogo.
 * @property onCancel Função callback executada ao cancelar ou fechar o diálogo.
 */
data class MessageDialogState(
    val dialogType: EnumDialogType = EnumDialogType.ERROR,
    val dialogMessage: String = "",
    val showDialog: Boolean = false,
    val onShowDialog: ShowMessageDialogCallback? = null,
    val onHideDialog: () -> Unit = { },
    val onConfirm: () -> Unit = { },
    val onCancel: () -> Unit = { }
)
