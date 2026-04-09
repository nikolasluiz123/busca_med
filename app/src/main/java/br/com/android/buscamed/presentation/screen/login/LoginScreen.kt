package br.com.android.buscamed.presentation.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.android.buscamed.R
import br.com.android.buscamed.presentation.core.components.buttons.BaseButton
import br.com.android.buscamed.presentation.core.components.buttons.BaseOutlinedButton
import br.com.android.buscamed.presentation.core.components.dialog.BaseMessageDialog
import br.com.android.buscamed.presentation.core.components.fields.text.OutlinedTextFieldPasswordValidation
import br.com.android.buscamed.presentation.core.components.fields.text.OutlinedTextFieldValidation
import br.com.android.buscamed.presentation.core.components.loading.BaseLinearProgressIndicator
import br.com.android.buscamed.presentation.core.components.topbar.SimpleTopAppBar
import br.com.android.buscamed.presentation.core.keyboard.EmailKeyboardOptions
import br.com.android.buscamed.presentation.core.keyboard.LastPasswordKeyboardOptions
import br.com.android.buscamed.presentation.core.permissions.RequestAllPermissions
import br.com.android.buscamed.presentation.core.theme.BuscaMedTheme
import br.com.android.buscamed.presentation.screen.login.LoginUIState
import br.com.android.buscamed.presentation.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateToRegister: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    RequestAllPermissions(context)

    LoginScreen(
        state = state,
        onLoginClick = {
            viewModel.login(onSuccess = onNavigateToHome)
        },
        onRegisterClick = onNavigateToRegister
    )
}

@Composable
fun LoginScreen(
    state: LoginUIState = LoginUIState(),
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = stringResource(R.string.login_screen_title),
                showNavigationIcon = false,
            )
        }
    ) { padding ->
        ConstraintLayout(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .testTag(LoginTestTag.SCREEN_CONTAINER.name)
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
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
            ) {

                ConstraintLayout(Modifier.fillMaxWidth()) {
                    val (emailRef, passwordRef, loginButtonRef, registerButtonRef) = createRefs()

                    BaseMessageDialog(state = state.messageDialogState)

                    OutlinedTextFieldValidation(
                        modifier = Modifier
                            .constrainAs(emailRef) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                width = Dimension.fillToConstraints
                            }
                            .testTag(LoginTestTag.EMAIL_FIELD.name),
                        field = state.email,
                        label = stringResource(R.string.login_screen_label_email),
                        keyboardOptions = EmailKeyboardOptions,
                    )

                    OutlinedTextFieldPasswordValidation(
                        modifier = Modifier
                            .constrainAs(passwordRef) {
                                start.linkTo(emailRef.start)
                                end.linkTo(emailRef.end)
                                top.linkTo(emailRef.bottom, margin = 8.dp)
                                width = Dimension.fillToConstraints
                            }
                            .testTag(LoginTestTag.PASSWORD_FIELD.name),
                        field = state.password,
                        label = stringResource(R.string.login_screen_label_password),
                        keyboardOptions = LastPasswordKeyboardOptions,
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                                onLoginClick()
                            }
                        )
                    )

                    createHorizontalChain(registerButtonRef, loginButtonRef)

                    BaseButton(
                        modifier = Modifier
                            .constrainAs(loginButtonRef) {
                                start.linkTo(parent.start)
                                top.linkTo(passwordRef.bottom, margin = 8.dp)
                                horizontalChainWeight = 0.45F
                                width = Dimension.fillToConstraints
                            }
                            .padding(start = 8.dp)
                            .testTag(LoginTestTag.LOGIN_BUTTON.name),
                        label = stringResource(R.string.login_screen_label_button_login),
                        enabled = state.showLoading.not(),
                        onClickListener = {
                            keyboardController?.hide()
                            onLoginClick()
                        }
                    )

                    BaseOutlinedButton(
                        modifier = Modifier
                            .constrainAs(registerButtonRef) {
                                end.linkTo(parent.end)
                                top.linkTo(passwordRef.bottom, margin = 8.dp)
                                horizontalChainWeight = 0.45F
                                width = Dimension.fillToConstraints
                            }
                            .testTag(LoginTestTag.REGISTER_BUTTON.name),
                        label = stringResource(R.string.login_screen_label_button_register),
                        enabled = state.showLoading.not(),
                        onClickListener = onRegisterClick
                    )
                }
            }
        }
    }
}


@Preview(device = "id:small_phone", apiLevel = 35)
@Composable
private fun LoginScreenPreview() {
    BuscaMedTheme {
        Surface {
            LoginScreen(state = LoginUIState())
        }
    }
}

@Preview(device = "id:small_phone", apiLevel = 35)
@Composable
private fun LoginScreenDarkPreview() {
    BuscaMedTheme(darkTheme = true) {
        Surface {
            LoginScreen(state = LoginUIState())
        }
    }
}

@Preview(device = "id:small_phone", apiLevel = 35)
@Composable
private fun LoginScreenDarkDisabledPreview() {
    BuscaMedTheme(darkTheme = true) {
        Surface {
            LoginScreen(
                state = LoginUIState(
                    showLoading = true
                )
            )
        }
    }
}

@Preview(device = "id:small_phone", apiLevel = 35)
@Composable
private fun LoginScreenDisabledPreview() {
    BuscaMedTheme() {
        Surface {
            LoginScreen(
                state = LoginUIState(
                    showLoading = true
                )
            )
        }
    }
}