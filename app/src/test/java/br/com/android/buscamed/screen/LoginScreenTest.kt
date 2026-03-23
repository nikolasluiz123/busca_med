package br.com.android.buscamed.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.android.buscamed.presentation.core.state.dialog.MessageDialogState
import br.com.android.buscamed.presentation.core.state.field.DefaultTextField
import br.com.android.buscamed.presentation.core.tags.GenericUIComponentTag
import br.com.android.buscamed.presentation.core.theme.BuscaMedTheme
import br.com.android.buscamed.presentation.screen.login.LoginScreen
import br.com.android.buscamed.presentation.screen.login.LoginTestTag
import br.com.android.buscamed.presentation.state.LoginUIState
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/**
 * Suite de testes de interface de usuário para o componente [LoginScreen].
 */
@RunWith(AndroidJUnit4::class)
@Config(instrumentedPackages = ["androidx.loader.content"])
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldDisplayAllInitialComponents() {
        composeTestRule.setContent {
            BuscaMedTheme {
                LoginScreen(state = LoginUIState())
            }
        }

        composeTestRule.onNodeWithTag(LoginTestTag.SCREEN_CONTAINER.name).assertIsDisplayed()
        composeTestRule.onNodeWithTag(LoginTestTag.EMAIL_FIELD.name).assertIsDisplayed()
        composeTestRule.onNodeWithTag(LoginTestTag.PASSWORD_FIELD.name).assertIsDisplayed()
        composeTestRule.onNodeWithTag(LoginTestTag.LOGIN_BUTTON.name).assertIsDisplayed()
        composeTestRule.onNodeWithTag(LoginTestTag.REGISTER_BUTTON.name).assertIsDisplayed()
    }

    @Test
    fun shouldShowLoadingIndicatorAndDisableButtonsWhenLoadingIsTrue() {
        val state = LoginUIState(showLoading = true)

        composeTestRule.setContent {
            BuscaMedTheme {
                LoginScreen(state = state)
            }
        }

        composeTestRule.onNodeWithTag(GenericUIComponentTag.LOADING_INDICATOR.name).assertIsDisplayed()
        composeTestRule.onNodeWithTag(LoginTestTag.LOGIN_BUTTON.name).assertIsNotEnabled()
        composeTestRule.onNodeWithTag(LoginTestTag.REGISTER_BUTTON.name).assertIsNotEnabled()
    }

    @Test
    fun shouldDisplayErrorMessageWhenEmailOrPasswordIsInvalid() {
        val emailErrorMsg = "E-mail inválido"
        val passwordErrorMsg = "Senha muito curta"
        
        val state = LoginUIState(
            email = DefaultTextField(errorMessage = emailErrorMsg),
            password = DefaultTextField(errorMessage = passwordErrorMsg)
        )

        composeTestRule.setContent {
            BuscaMedTheme {
                LoginScreen(state = state)
            }
        }

        composeTestRule.onNodeWithText(emailErrorMsg).assertIsDisplayed()
        composeTestRule.onNodeWithText(passwordErrorMsg).assertIsDisplayed()
    }

    @Test
    fun shouldTriggerOnLoginClickWhenLoginButtonIsClicked() {
        var loginClicked = false

        composeTestRule.setContent {
            BuscaMedTheme {
                LoginScreen(
                    state = LoginUIState(),
                    onLoginClick = { loginClicked = true }
                )
            }
        }

        composeTestRule.onNodeWithTag(LoginTestTag.LOGIN_BUTTON.name).performClick()

        assertTrue(loginClicked)
    }

    @Test
    fun shouldTriggerOnRegisterClickWhenRegisterButtonIsClicked() {
        var registerClicked = false

        composeTestRule.setContent {
            BuscaMedTheme {
                LoginScreen(
                    state = LoginUIState(),
                    onRegisterClick = { registerClicked = true }
                )
            }
        }

        composeTestRule.onNodeWithTag(LoginTestTag.REGISTER_BUTTON.name).performClick()

        assertTrue(registerClicked)
    }

    @Test
    fun shouldShowErrorDialogWhenMessageDialogStateIsSet() {
        val dialogMessage = "Credenciais incorretas"
        val state = LoginUIState(
            messageDialogState = MessageDialogState(
                showDialog = true,
                dialogMessage = dialogMessage
            )
        )

        composeTestRule.setContent {
            BuscaMedTheme {
                LoginScreen(state = state)
            }
        }

        composeTestRule.onNodeWithTag(GenericUIComponentTag.MESSAGE_DIALOG_CONTAINER.name).assertIsDisplayed()
        composeTestRule.onNodeWithTag(GenericUIComponentTag.MESSAGE_DIALOG_TEXT.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(dialogMessage).assertIsDisplayed()
    }
}