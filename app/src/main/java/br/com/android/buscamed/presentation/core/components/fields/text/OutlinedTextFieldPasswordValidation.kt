package br.com.android.buscamed.presentation.core.components.fields.text

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import br.com.android.buscamed.R
import br.com.android.buscamed.presentation.core.state.field.DefaultTextField
import br.com.android.buscamed.presentation.core.theme.InputTextStyle
import kotlin.properties.Delegates

@Composable
fun OutlinedTextFieldPasswordValidation(
    field: DefaultTextField,
    label: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions,
    maxLength: Int? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextFieldPasswordValidation(
        value = field.value,
        onValueChange = field.onChange,
        error = field.errorMessage,
        modifier = modifier,
        label = { Text(text = label, style = InputTextStyle) },
        keyboardOptions = keyboardOptions,
        maxLength = maxLength,
        keyboardActions = keyboardActions
    )
}

@Composable
fun OutlinedTextFieldPasswordValidation(
    field: DefaultTextField,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions,
    label: @Composable (() -> Unit)? = null,
    maxLength: Int? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextFieldPasswordValidation(
        value = field.value,
        onValueChange = field.onChange,
        error = field.errorMessage,
        modifier = modifier,
        label = label,
        keyboardOptions = keyboardOptions,
        maxLength = maxLength,
        keyboardActions = keyboardActions
    )
}

@Composable
fun OutlinedTextFieldPasswordValidation(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    error: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
    maxLength: Int? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    OutlinedTextFieldValidation(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        error = error,
        label = label,
        keyboardOptions = keyboardOptions,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            lateinit var description: String
            var resource by Delegates.notNull<Int>()

            if (passwordVisible) {
                resource = R.drawable.ic_visibility_24dp
                description = stringResource(R.string.description_icon_hide_password)
            } else {
                resource = R.drawable.ic_visibility_off_24dp
                description = stringResource(R.string.description_icon_show_password)
            }

            IconButton(
                onClick = { passwordVisible = !passwordVisible }
            ) {
                Icon(
                    painter = painterResource(resource),
                    description,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        maxLength = maxLength,
        keyboardActions = keyboardActions
    )
}