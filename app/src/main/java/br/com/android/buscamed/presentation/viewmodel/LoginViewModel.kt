package br.com.android.buscamed.presentation.viewmodel

import android.content.Context
import br.com.android.buscamed.R
import br.com.android.buscamed.domain.core.validation.FieldValidationError
import br.com.android.buscamed.domain.core.validation.GeneralValidationError
import br.com.android.buscamed.domain.core.validation.UseCaseResult
import br.com.android.buscamed.domain.core.validation.ValidationError
import br.com.android.buscamed.domain.model.UserCredentials
import br.com.android.buscamed.domain.usecase.authentication.DefaultAuthenticationUseCase
import br.com.android.buscamed.domain.usecase.authentication.enumeration.UserCredentialsFieldErrorType
import br.com.android.buscamed.domain.usecase.authentication.enumeration.UserCredentialsFieldValidation
import br.com.android.buscamed.domain.usecase.authentication.enumeration.UserCredentialsGeneralErrorType
import br.com.android.buscamed.presentation.core.components.dialog.showErrorDialog
import br.com.android.buscamed.presentation.core.state.enumeration.EnumDialogType
import br.com.android.buscamed.presentation.state.LoginUIState
import br.com.android.buscamed.presentation.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val authenticationUseCase: DefaultAuthenticationUseCase
): BaseViewModel() {
    private val _uiState: MutableStateFlow<LoginUIState> = MutableStateFlow(LoginUIState())
    val uiState get() = _uiState.asStateFlow()

    init {
        initialLoadUIState()
    }

    override fun initialLoadUIState() {
        _uiState.value = _uiState.value.copy(
            email = createTextFieldState(
                getCurrentState = { _uiState.value.email },
                updateState = { _uiState.value = _uiState.value.copy(email = it) }
            ),
            password = createTextFieldState(
                getCurrentState = { _uiState.value.password },
                updateState = { _uiState.value = _uiState.value.copy(password = it) }
            ),
            messageDialogState = createMessageDialogState(
                getCurrentState = { _uiState.value.messageDialogState },
                updateState = { _uiState.value = _uiState.value.copy(messageDialogState = it) }
            ),
            onToggleLoading = {
                _uiState.value = _uiState.value.copy(showLoading = !_uiState.value.showLoading)
            },
        )
    }

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return context.getString(R.string.validation_error_unknown)
    }

    override fun onShowErrorDialog(message: String) {
        _uiState.value.messageDialogState.onShowDialog?.showErrorDialog(message = message)
    }

    fun login(onSuccess: () -> Unit) {
        launch {
            _uiState.value.onToggleLoading()

            val email = _uiState.value.email.value
            val password = _uiState.value.password.value

            when (val result = authenticationUseCase(UserCredentials(email, password))) {
                is UseCaseResult.Success -> onSuccess()
                is UseCaseResult.Error -> {
                    logValidationWarnings(result.errors)
                    showValidationMessages(result.errors)
                }
            }

            _uiState.value.onToggleLoading()
        }
    }

    private fun showValidationMessages(errors: List<ValidationError>) {
        errors.forEach { error ->
            when (error) {
                is FieldValidationError<*, *> -> {
                    val errorMessage = getFieldErrorMessage(
                        error.type as UserCredentialsFieldErrorType,
                        error.field as UserCredentialsFieldValidation
                    )

                    when (error.field) {
                        UserCredentialsFieldValidation.EMAIL -> {
                            _uiState.value = _uiState.value.copy(
                                email = _uiState.value.email.copy(errorMessage = errorMessage)
                            )
                        }
                        UserCredentialsFieldValidation.PASSWORD -> {
                            _uiState.value = _uiState.value.copy(
                                password = _uiState.value.password.copy(errorMessage = errorMessage)
                            )
                        }
                    }
                }
                is GeneralValidationError<*> -> {
                    val errorMessage = getGeneralErrorMessage(error.type as UserCredentialsGeneralErrorType)
                    _uiState.value.messageDialogState.onShowDialog?.onShow(
                        EnumDialogType.ERROR,
                        errorMessage,
                        {},
                        {}
                    )
                }
            }
        }
    }

    private fun getFieldErrorMessage(
        type: UserCredentialsFieldErrorType,
        validation: UserCredentialsFieldValidation
    ): String {
        val fieldLabel = when (validation) {
            UserCredentialsFieldValidation.EMAIL -> {
                context.getString(R.string.login_screen_label_email)
            }

            UserCredentialsFieldValidation.PASSWORD -> {
                context.getString(R.string.login_screen_label_password)
            }
        }

        return when (type) {
            UserCredentialsFieldErrorType.REQUIRED -> {
                context.getString(R.string.validation_error_required_field, fieldLabel)
            }
        }
    }

    private fun getGeneralErrorMessage(type: UserCredentialsGeneralErrorType): String {
        return when (type) {
            UserCredentialsGeneralErrorType.INVALID_CREDENTIALS -> context.getString(R.string.login_screen_validation_error_invalid_credentials)
            UserCredentialsGeneralErrorType.ACCOUNT_BLOCKED -> context.getString(R.string.login_screen_validation_error_account_blocked)
            UserCredentialsGeneralErrorType.NETWORK_ERROR -> context.getString(R.string.validation_error_network)
        }
    }
}