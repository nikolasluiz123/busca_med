package br.com.android.buscamed.presentation.screen.registeruser

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.android.buscamed.R
import br.com.android.buscamed.presentation.core.components.buttons.fab.FloatingActionButtonSave
import br.com.android.buscamed.presentation.core.components.dialog.BaseMessageDialog
import br.com.android.buscamed.presentation.core.components.fields.text.OutlinedTextFieldPasswordValidation
import br.com.android.buscamed.presentation.core.components.fields.text.OutlinedTextFieldValidation
import br.com.android.buscamed.presentation.core.components.loading.BaseLinearProgressIndicator
import br.com.android.buscamed.presentation.core.components.topbar.SimpleTopAppBar
import br.com.android.buscamed.presentation.core.keyboard.EmailKeyboardOptions
import br.com.android.buscamed.presentation.core.keyboard.LastPasswordKeyboardOptions
import br.com.android.buscamed.presentation.core.keyboard.PersonNameKeyboardOptions
import br.com.android.buscamed.presentation.state.RegisterUserUIState
import br.com.android.buscamed.presentation.viewmodel.RegisterUserViewModel

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
        onSaveClick = {
            viewModel.save(onSuccess = onSaveSuccess)
        }
    )
}

@Composable
fun RegisterUserScreen(
    state: RegisterUserUIState,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = state.title,
                showNavigationIcon = true,
                onBackClick = onBackClick
            )
        },
        floatingActionButton = {
            FloatingActionButtonSave(
                iconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                onClick = {
                    keyboardController?.hide()
                    state.onToggleLoading()
                    onSaveClick()
                }
            )
        }
    ) { padding ->
        ConstraintLayout(
            Modifier
                .fillMaxSize()
                .padding(padding)
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
                            },
                        field = state.name,
                        label = stringResource(R.string.register_user_label_name),
                        keyboardOptions = PersonNameKeyboardOptions
                    )

                    OutlinedTextFieldValidation(
                        modifier = Modifier
                            .constrainAs(emailRef) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(nameRef.bottom, margin = 8.dp)
                                width = Dimension.fillToConstraints
                            },
                        field = state.email,
                        label = stringResource(R.string.login_screen_label_email),
                        keyboardOptions = EmailKeyboardOptions
                    )

                    OutlinedTextFieldPasswordValidation(
                        modifier = Modifier
                            .constrainAs(passwordRef) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(emailRef.bottom, margin = 8.dp)
                                width = Dimension.fillToConstraints
                            },
                        field = state.password,
                        label = stringResource(R.string.login_screen_label_password),
                        keyboardOptions = LastPasswordKeyboardOptions,
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                                onSaveClick()
                            }
                        )
                    )
                }
            }
        }
    }
}