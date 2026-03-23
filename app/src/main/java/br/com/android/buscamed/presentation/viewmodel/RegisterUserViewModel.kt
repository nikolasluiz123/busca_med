package br.com.android.buscamed.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.android.buscamed.R
import br.com.android.buscamed.domain.core.validation.FieldValidationError
import br.com.android.buscamed.domain.core.validation.GeneralValidationError
import br.com.android.buscamed.domain.core.validation.UseCaseResult
import br.com.android.buscamed.domain.core.validation.ValidationError
import br.com.android.buscamed.domain.model.User
import br.com.android.buscamed.domain.repository.UserRepository
import br.com.android.buscamed.domain.usecase.registeruser.RegisterUserUseCase
import br.com.android.buscamed.domain.usecase.registeruser.enumeration.UserField
import br.com.android.buscamed.domain.usecase.registeruser.enumeration.UserFieldErrorType
import br.com.android.buscamed.domain.usecase.registeruser.enumeration.UserGeneralErrorType
import br.com.android.buscamed.presentation.core.extensions.fromJsonNavParamToArgs
import br.com.android.buscamed.presentation.core.state.enumeration.EnumDialogType
import br.com.android.buscamed.presentation.screen.registeruser.RegisterUserScreenArgs
import br.com.android.buscamed.presentation.screen.registeruser.registerUserArguments
import br.com.android.buscamed.presentation.state.RegisterUserUIState
import br.com.android.buscamed.presentation.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterUserViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val registerUserUseCase: RegisterUserUseCase,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<RegisterUserUIState> =
        MutableStateFlow(RegisterUserUIState())
    val uiState get() = _uiState.asStateFlow()

    private val jsonArgs: String? = savedStateHandle[registerUserArguments]

    init {
        initialLoadUIState()
        loadUserData()
    }

    override fun initialLoadUIState() {
        val args = jsonArgs?.fromJsonNavParamToArgs(RegisterUserScreenArgs::class.java)!!

        val isEditMode = args.userId.isNullOrEmpty()
        val titleResId = if (isEditMode) {
            R.string.register_user_screen_title_register_user
        } else {
            R.string.register_user_screen_title_edit_user
        }

        _uiState.value = _uiState.value.copy(
            isEditMode = isEditMode,
            userId = args.userId,
            title = context.getString(titleResId),
            name = createTextFieldState(
                getCurrentState = { _uiState.value.name },
                updateState = { _uiState.value = _uiState.value.copy(name = it) }
            ),
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
            }
        )
    }

    private fun loadUserData() {
        val userId = _uiState.value.userId ?: return

        viewModelScope.launch {
            _uiState.value.onToggleLoading()

            val user = userRepository.getById(userId)
            user?.let {
                _uiState.value = _uiState.value.copy(
                    name = _uiState.value.name.copy(value = it.name),
                    email = _uiState.value.email.copy(value = it.email)
                )
            }

            _uiState.value.onToggleLoading()
        }
    }

    fun save(onSuccess: () -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch {
            val user = User(
                id = _uiState.value.userId,
                name = _uiState.value.name.value,
                email = _uiState.value.email.value,
                password = _uiState.value.password.value.ifBlank { null }
            )

            when (val result = registerUserUseCase(user)) {
                is UseCaseResult.Success -> {
                    onSuccess()
                }

                is UseCaseResult.Error -> {
                    showValidationMessages(result.errors)
                    onFailure()
                }
            }
        }
    }

    private fun showValidationMessages(errors: List<ValidationError>) {
        errors.forEach { error ->
            when (error) {
                is FieldValidationError<*, *> -> {
                    val errorMessage = getFieldErrorMessage(
                        type = error.type as UserFieldErrorType,
                        field = error.field as UserField
                    )

                    when (error.field) {
                        UserField.EMAIL -> {
                            _uiState.value = _uiState.value.copy(
                                email = _uiState.value.email.copy(errorMessage = errorMessage)
                            )
                        }

                        UserField.PASSWORD -> {
                            _uiState.value = _uiState.value.copy(
                                password = _uiState.value.password.copy(errorMessage = errorMessage)
                            )
                        }
                    }
                }

                is GeneralValidationError<*> -> {
                    val errorMessage = getGeneralErrorMessage(error.type as UserGeneralErrorType)
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

    private fun getFieldErrorMessage(type: UserFieldErrorType, field: UserField): String {
        val fieldLabel = when (field) {
            UserField.EMAIL -> {
                context.getString(R.string.register_user_screen_label_email)
            }

            UserField.PASSWORD -> {
                context.getString(R.string.register_user_screen_label_password)
            }
        }

        return when (type) {
            UserFieldErrorType.REQUIRED -> {
                context.getString(R.string.validation_error_required_field, fieldLabel)
            }

            UserFieldErrorType.INVALID_FORMAT -> {
                context.getString(R.string.validation_error_invalid, fieldLabel)
            }

            UserFieldErrorType.TOO_SHORT -> {
                context.getString(
                    R.string.validation_error_too_short,
                    fieldLabel,
                    field.minLength.toString()
                )
            }

            UserFieldErrorType.TOO_LONG -> {
                context.getString(
                    R.string.validation_error_too_long,
                    fieldLabel,
                    field.maxLength.toString()
                )
            }
        }
    }

    private fun getGeneralErrorMessage(type: UserGeneralErrorType): String {
        return when (type) {
            UserGeneralErrorType.EMAIL_ALREADY_IN_USE -> {
                context.getString(R.string.register_user_screen_validation_error_email_in_use)
            }

            UserGeneralErrorType.NETWORK_ERROR -> {
                context.getString(R.string.validation_error_network)
            }

            UserGeneralErrorType.UNKNOWN_ERROR -> {
                context.getString(R.string.validation_error_unknown)
            }
        }
    }
}