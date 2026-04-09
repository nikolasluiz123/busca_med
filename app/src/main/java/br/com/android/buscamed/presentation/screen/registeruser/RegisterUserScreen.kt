package br.com.android.buscamed.presentation.screen.registeruser

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.android.buscamed.R
import br.com.android.buscamed.presentation.core.callbacks.OnSave
import br.com.android.buscamed.presentation.core.components.buttons.fab.FloatingActionButtonSave
import br.com.android.buscamed.presentation.core.components.dialog.BaseMessageDialog
import br.com.android.buscamed.presentation.core.components.fields.text.OutlinedTextFieldPasswordValidation
import br.com.android.buscamed.presentation.core.components.fields.text.OutlinedTextFieldValidation
import br.com.android.buscamed.presentation.core.components.loading.BaseLinearProgressIndicator
import br.com.android.buscamed.presentation.core.components.topbar.SimpleTopAppBar
import br.com.android.buscamed.presentation.core.keyboard.EmailKeyboardOptions
import br.com.android.buscamed.presentation.core.keyboard.LastPasswordKeyboardOptions
import br.com.android.buscamed.presentation.core.keyboard.PersonNameKeyboardOptions
import br.com.android.buscamed.presentation.core.theme.SnackBarTextStyle
import br.com.android.buscamed.presentation.screen.registeruser.RegisterUserUIState
import br.com.android.buscamed.presentation.viewmodel.RegisterUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RegisterUserScreen(
    viewModel: RegisterUserViewModel,
    onBackClick: () -> Unit,
    onSaveSuccess: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    RegisterUserScreen(
        state = state,
        onBackClick = onBackClick,
        onSaveClick = viewModel::save,
        onSaveSuccess = onSaveSuccess
    )
}

@Composable
fun RegisterUserScreen(
    state: RegisterUserUIState,
    onBackClick: () -> Unit,
    onSaveClick: OnSave,
    onSaveSuccess: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = state.title,
                showNavigationIcon = true,
                onBackClick = onBackClick
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                Snackbar(modifier = Modifier.padding(8.dp)) {
                    Text(text = it.visuals.message, style = SnackBarTextStyle)
                }
            }
        },
        floatingActionButton = {
            FloatingActionButtonSave(
                modifier = Modifier.testTag(RegisterUserTestTag.SAVE_FAB.name),
                iconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                onClick = {
                    keyboardController?.hide()
                    state.onToggleLoading()
                    onSaveClick.execute(
                        onSuccess = {
                            state.onToggleLoading()
                            showSuccessMessage(coroutineScope, snackbarHostState, context)
                            onSaveSuccess()
                        },
                        onFailure = {
                            state.onToggleLoading()
                        }
                    )
                }
            )
        }
    ) { padding ->
        ConstraintLayout(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .testTag(RegisterUserTestTag.SCREEN_CONTAINER.name)
        ) {
            val (loadingRef, containerRef) = createRefs()

            Row(
                Modifier
                    .fillMaxWidth()
                    .constrainAs(loadingRef) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
            ) {
                BaseLinearProgressIndicator(state.showLoading)
            }

            Column(
                modifier = Modifier
                    .constrainAs(containerRef) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(loadingRef.bottom, margin = 16.dp)
                        bottom.linkTo(parent.bottom)
                    }
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
            ) {
                ConstraintLayout(Modifier.fillMaxWidth()) {
                    val (nameRef, emailRef, passwordRef) = createRefs()

                    BaseMessageDialog(state = state.messageDialogState)

                    OutlinedTextFieldValidation(
                        modifier = Modifier
                            .constrainAs(nameRef) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                width = Dimension.fillToConstraints
                            }
                            .testTag(RegisterUserTestTag.NAME_FIELD.name),
                        field = state.name,
                        label = stringResource(R.string.register_user_screen_label_name_field),
                        keyboardOptions = PersonNameKeyboardOptions
                    )

                    OutlinedTextFieldValidation(
                        modifier = Modifier
                            .constrainAs(emailRef) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(nameRef.bottom, margin = 8.dp)
                                width = Dimension.fillToConstraints
                            }
                            .testTag(RegisterUserTestTag.EMAIL_FIELD.name),
                        field = state.email,
                        label = stringResource(R.string.register_user_screen_label_email_field),
                        keyboardOptions = EmailKeyboardOptions
                    )

                    OutlinedTextFieldPasswordValidation(
                        modifier = Modifier
                            .constrainAs(passwordRef) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(emailRef.bottom, margin = 8.dp)
                                width = Dimension.fillToConstraints
                            }
                            .testTag(RegisterUserTestTag.PASSWORD_FIELD.name),
                        field = state.password,
                        label = stringResource(R.string.register_screen_label_password_field),
                        keyboardOptions = LastPasswordKeyboardOptions,
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                                state.onToggleLoading()
                                onSaveClick.execute(
                                    onSuccess = {
                                        state.onToggleLoading()
                                        showSuccessMessage(coroutineScope, snackbarHostState, context)
                                        onSaveSuccess()
                                    },
                                    onFailure = {
                                        state.onToggleLoading()
                                    }
                                )
                            }
                        )
                    )
                }
            }
        }
    }
}

private fun showSuccessMessage(
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    context: Context
) {
    coroutineScope.launch {
        snackbarHostState.showSnackbar(
            message = context.getString(R.string.register_user_screen_success_message)
        )
    }
}