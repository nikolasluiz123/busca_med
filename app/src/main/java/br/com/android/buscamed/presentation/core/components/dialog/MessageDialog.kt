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
import androidx.compose.ui.res.stringResource
import br.com.android.buscamed.R
import br.com.android.buscamed.presentation.core.components.buttons.DefaultDialogTextButton
import br.com.android.buscamed.presentation.core.state.dialog.MessageDialogState
import br.com.android.buscamed.presentation.core.state.enumeration.EnumDialogType
import br.com.android.buscamed.presentation.core.theme.DialogTitleTextStyle
import br.com.android.buscamed.presentation.core.theme.ValueTextStyle
import com.google.firebase.Firebase

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
            onDismissRequest = onDismissRequest,
            title = {
                Text(
                    text = stringResource(type.titleResId),
                    style = DialogTitleTextStyle
                )
            },
            text = {
                Box(modifier = Modifier.verticalScroll(state = scrollState)) {
                    Text(
                        text = message,
                        style = ValueTextStyle
                    )
                }
            },
            confirmButton = {
                when (type) {
                    EnumDialogType.ERROR, EnumDialogType.INFORMATION -> {
                        DefaultDialogTextButton(
                            labelResId = R.string.label_ok,
                            onClick = {
                                onDismissRequest()
                                onConfirm()
                            }
                        )
                    }

                    EnumDialogType.CONFIRMATION -> {
                        DefaultDialogTextButton(
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