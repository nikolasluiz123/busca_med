package br.com.android.buscamed.presentation.core.components.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import br.com.android.buscamed.R
import br.com.android.buscamed.presentation.core.components.buttons.DefaultDialogTextButton
import br.com.android.buscamed.presentation.core.state.dialog.MessageDialogState
import br.com.android.buscamed.presentation.core.state.enumeration.EnumDialogType
import br.com.android.buscamed.presentation.core.tags.GenericUIComponentTag
import br.com.android.buscamed.presentation.core.theme.DialogTitleTextStyle
import br.com.android.buscamed.presentation.core.theme.ValueTextStyle

/**
 * Componente de diálogo de mensagem baseado em estado.
 *
 * Facilita a exibição de diálogos utilizando o [MessageDialogState], mapeando
 * automaticamente as propriedades do estado para os parâmetros do componente visual.
 *
 * @param state O estado que controla a visibilidade e o conteúdo do diálogo.
 */
@Composable
fun BaseMessageDialog(state: MessageDialogState) {
    BaseMessageDialog(
        type = state.dialogType,
        show = state.showDialog,
        onDismissRequest = state.onHideDialog,
        message = state.dialogMessage,
        onConfirm = state.onConfirm,
        onCancel = state.onCancel
    )
}

/**
 * Componente de diálogo de mensagem padronizado (AlertDialog).
 *
 * Provê uma interface consistente para exibição de erros, confirmações e informações,
 * com suporte a rolagem de texto e botões de ação configuráveis por tipo.
 *
 * @param type O tipo do diálogo, que define o título e o comportamento dos botões.
 * @param show Controla a exibição do diálogo.
 * @param onDismissRequest Função executada ao tentar fechar o diálogo.
 * @param message O texto da mensagem a ser exibida.
 * @param onConfirm Função executada ao confirmar a ação.
 * @param onCancel Função executada ao cancelar a ação.
 * @param containerColor Cor de fundo do contêiner do diálogo.
 * @param textContentColor Cor do texto da mensagem.
 */
@Composable
fun BaseMessageDialog(
    type: EnumDialogType,
    show: Boolean,
    onDismissRequest: () -> Unit,
    message: String,
    onConfirm: () -> Unit = { },
    onCancel: () -> Unit = { },
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    textContentColor: Color = MaterialTheme.colorScheme.onSurface
) {
    val scrollState = rememberScrollState()

    if (show) {
        AlertDialog(
            modifier = Modifier.testTag(GenericUIComponentTag.MESSAGE_DIALOG_CONTAINER.name),
            onDismissRequest = onDismissRequest,
            title = {
                Text(
                    modifier = Modifier.testTag(GenericUIComponentTag.MESSAGE_DIALOG_TITLE.name),
                    text = stringResource(type.titleResId),
                    style = DialogTitleTextStyle
                )
            },
            text = {
                Box(modifier = Modifier.verticalScroll(state = scrollState)) {
                    Text(
                        modifier = Modifier.testTag(GenericUIComponentTag.MESSAGE_DIALOG_TEXT.name),
                        text = message,
                        style = ValueTextStyle
                    )
                }
            },
            confirmButton = {
                when (type) {
                    EnumDialogType.ERROR, EnumDialogType.INFORMATION -> {
                        DefaultDialogTextButton(
                            modifier = Modifier.testTag(GenericUIComponentTag.MESSAGE_DIALOG_CONFIRM_BUTTON.name),
                            labelResId = R.string.label_ok,
                            onClick = {
                                onDismissRequest()
                                onConfirm()
                            }
                        )
                    }

                    EnumDialogType.CONFIRMATION -> {
                        DefaultDialogTextButton(
                            modifier = Modifier.testTag(GenericUIComponentTag.MESSAGE_DIALOG_CONFIRM_BUTTON.name),
                            labelResId = R.string.label_confirm,
                            onClick = {
                                onDismissRequest()
                                onConfirm()
                            }
                        )
                    }
                }
            },
            dismissButton = {
                when (type) {
                    EnumDialogType.CONFIRMATION -> {
                        DefaultDialogTextButton(
                            modifier = Modifier.testTag(GenericUIComponentTag.MESSAGE_DIALOG_CANCEL_BUTTON.name),
                            labelResId = R.string.label_cancel,
                            onClick = {
                                onDismissRequest()
                                onCancel()
                            }
                        )
                    }

                    EnumDialogType.ERROR, EnumDialogType.INFORMATION -> {}
                }
            },
            containerColor = containerColor,
            textContentColor = textContentColor,
        )
    }
}
