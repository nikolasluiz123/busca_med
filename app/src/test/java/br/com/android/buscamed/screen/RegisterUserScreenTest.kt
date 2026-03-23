package br.com.android.buscamed.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.android.buscamed.presentation.core.callbacks.OnSave
import br.com.android.buscamed.presentation.core.state.field.DefaultTextField
import br.com.android.buscamed.presentation.core.tags.GenericUIComponentTag
import br.com.android.buscamed.presentation.core.theme.BuscaMedTheme
import br.com.android.buscamed.presentation.screen.registeruser.RegisterUserScreen
import br.com.android.buscamed.presentation.screen.registeruser.RegisterUserTestTag
import br.com.android.buscamed.presentation.state.RegisterUserUIState
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/**
 * Suite de testes de interface de usuário para o componente [RegisterUserScreen].
 */
@RunWith(AndroidJUnit4::class)
@Config(instrumentedPackages = ["androidx.loader.content"])
class RegisterUserScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldDisplayAllInitialComponentsInRegisterMode() {
        val state = RegisterUserUIState(
            title = "Registrar Novo Usuário",
            isEditMode = false
        )

        composeTestRule.setContent {
            BuscaMedTheme {
                RegisterUserScreen(
                    state = state,
                    onBackClick = {},
                    onSaveClick = { _, _ -> },
                    onSaveSuccess = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Registrar Novo Usuário").assertIsDisplayed()
        composeTestRule.onNodeWithTag(RegisterUserTestTag.SCREEN_CONTAINER.name).assertIsDisplayed()
        composeTestRule.onNodeWithTag(RegisterUserTestTag.NAME_FIELD.name).assertIsDisplayed()
        composeTestRule.onNodeWithTag(RegisterUserTestTag.EMAIL_FIELD.name).assertIsDisplayed()
        composeTestRule.onNodeWithTag(RegisterUserTestTag.PASSWORD_FIELD.name).assertIsDisplayed()
        composeTestRule.onNodeWithTag(RegisterUserTestTag.SAVE_FAB.name).assertIsDisplayed()
    }

    @Test
    fun shouldDisplayAllInitialComponentsInEditMode() {
        val state = RegisterUserUIState(
            title = "Editar Usuário",
            isEditMode = true
        )

        composeTestRule.setContent {
            BuscaMedTheme {
                RegisterUserScreen(
                    state = state,
                    onBackClick = {},
                    onSaveClick = { _, _ -> },
                    onSaveSuccess = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Editar Usuário").assertIsDisplayed()
        composeTestRule.onNodeWithTag(RegisterUserTestTag.NAME_FIELD.name).assertIsDisplayed()
    }

    @Test
    fun shouldShowLoadingIndicatorWhenLoadingIsTrue() {
        val state = RegisterUserUIState(showLoading = true)

        composeTestRule.setContent {
            BuscaMedTheme {
                RegisterUserScreen(
                    state = state,
                    onBackClick = {},
                    onSaveClick = { _, _ -> },
                    onSaveSuccess = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(GenericUIComponentTag.LOADING_INDICATOR.name).assertIsDisplayed()
    }

    @Test
    fun shouldDisplayErrorMessagesForFieldsWhenConfiguredInState() {
        val nameErrorMsg = "Nome obrigatório"
        val emailErrorMsg = "Formato inválido"
        
        val state = RegisterUserUIState(
            name = DefaultTextField(errorMessage = nameErrorMsg),
            email = DefaultTextField(errorMessage = emailErrorMsg)
        )

        composeTestRule.setContent {
            BuscaMedTheme {
                RegisterUserScreen(
                    state = state,
                    onBackClick = {},
                    onSaveClick = { _, _ -> },
                    onSaveSuccess = {}
                )
            }
        }

        composeTestRule.onNodeWithText(nameErrorMsg).assertIsDisplayed()
        composeTestRule.onNodeWithText(emailErrorMsg).assertIsDisplayed()
    }

    @Test
    fun shouldTriggerOnSaveClickWhenFabIsClicked() {
        var saveClicked = false
        val onSaveAction = OnSave { onSuccess, _ ->
            saveClicked = true
            onSuccess()
        }

        composeTestRule.setContent {
            BuscaMedTheme {
                RegisterUserScreen(
                    state = RegisterUserUIState(),
                    onBackClick = {},
                    onSaveClick = onSaveAction,
                    onSaveSuccess = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(RegisterUserTestTag.SAVE_FAB.name).performClick()

        assertTrue(saveClicked)
    }

    @Test
    fun shouldTriggerOnBackClickWhenTopBarBackIsClicked() {
        var backClicked = false

        composeTestRule.setContent {
            BuscaMedTheme {
                RegisterUserScreen(
                    state = RegisterUserUIState(),
                    onBackClick = { backClicked = true },
                    onSaveClick = { _, _ -> },
                    onSaveSuccess = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Voltar").performClick()

        assertTrue(backClicked)
    }
}