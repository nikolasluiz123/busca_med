package br.com.android.buscamed.presentation.core.components.fields.text

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import br.com.android.buscamed.R
import br.com.android.buscamed.presentation.core.state.interfaces.field.TextField
import br.com.android.buscamed.presentation.core.theme.FieldErrorTextStyle
import br.com.android.buscamed.presentation.core.theme.InputTextStyle

/**
 * Campo de texto com contorno (Outlined) que integra validação visual automática.
 *
 * Esta função exibe mensagens de erro e ícones de alerta quando o estado do campo
 * indica uma falha de validação. É a versão principal que aceita a interface [TextField].
 *
 * @param field Interface que provê o valor, a função de mudança e a mensagem de erro.
 * @param label Rótulo textual do campo.
 * @param modifier Modificador de layout.
 * @param enabled Define se o campo está ativo.
 * @param readOnly Define se o campo é apenas para leitura.
 * @param textStyle Estilo visual do texto de entrada.
 * @param placeholder Componente opcional para exibição de dica quando o campo está vazio.
 * @param leadingIcon Ícone opcional no início do campo.
 * @param isError Indica se o campo deve exibir o estado de erro.
 * @param trailingIcon Ícone opcional no final do campo (padrão exibe ícone de erro se necessário).
 * @param visualTransformation Transformação visual do texto (ex: máscara de senha).
 * @param keyboardOptions Configurações do teclado virtual.
 * @param keyboardActions Ações executadas ao interagir com o teclado.
 * @param singleLine Define se o texto deve ser mantido em uma única linha.
 * @param maxLines Quantidade máxima de linhas visíveis.
 * @param interactionSource Fonte de interações do componente.
 * @param shape Forma geométrica do contorno do campo.
 * @param colors Configuração de cores para os diferentes estados do campo.
 * @param maxLength Limite máximo de caracteres permitidos.
 */
@Composable
fun OutlinedTextFieldValidation(
    field: TextField,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = field.errorMessage.isNotEmpty(),
    trailingIcon: @Composable (() -> Unit)? = {
        if (field.errorMessage.isNotEmpty())
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = stringResource(R.string.outlined_textfield_validation_error_icon_description),
                tint = MaterialTheme.colorScheme.error
            )
    },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = getDefaultOutlinedTextFieldColors(),
    maxLength: Int? = null,
) {
    OutlinedTextFieldValidation(
        value = field.value,
        onValueChange = field.onChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = { Text(text = label, style = InputTextStyle) },
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        error = field.errorMessage,
        isError = isError,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
        maxLength = maxLength,
    )
}

/**
 * Sobrecarga que aceita um componente [TextField] e um Composable para o rótulo.
 */
@Composable
fun OutlinedTextFieldValidation(
    field: TextField,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = field.errorMessage.isNotEmpty(),
    trailingIcon: @Composable (() -> Unit)? = {
        if (field.errorMessage.isNotEmpty())
            Icon(
                Icons.Default.Warning,
                "error",
                tint = MaterialTheme.colorScheme.error
            )
    },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = getDefaultOutlinedTextFieldColors(),
) {
    OutlinedTextFieldValidation(
        value = field.value,
        onValueChange = field.onChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        error = field.errorMessage,
        isError = isError,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors
    )
}

/**
 * Implementação base do campo de texto com validação que gerencia a exibição da mensagem de erro (supportingText).
 */
@Composable
fun OutlinedTextFieldValidation(
    value: String?,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = InputTextStyle,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    error: String = "",
    isError: Boolean = error.isNotEmpty(),
    trailingIcon: @Composable (() -> Unit)? = {
        if (error.isNotEmpty())
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "error",
                tint = MaterialTheme.colorScheme.error
            )
    },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = getDefaultOutlinedTextFieldColors(),
    maxLength: Int? = null,
) {
    OutlinedTextField(
        enabled = enabled,
        readOnly = readOnly,
        modifier = modifier,
        value = value ?: "",
        onValueChange = {
            if(maxLength == null || it.length <= maxLength) {
                onValueChange(it)
            }
        },
        singleLine = singleLine,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        supportingText = {
            if (error.isNotEmpty()) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = FieldErrorTextStyle
                )
            }
        },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = maxLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors
    )
}

/**
 * Retorna as cores padrão configuradas para os campos Outlined do projeto.
 */
@Composable
fun getDefaultOutlinedTextFieldColors(): TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
        cursorColor = MaterialTheme.colorScheme.secondary,
        unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
        unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
        unfocusedTrailingIconColor = MaterialTheme.colorScheme.secondary,
        unfocusedTextColor = MaterialTheme.colorScheme.secondary,
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.secondary,
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        focusedLabelColor = MaterialTheme.colorScheme.primary,
        focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
        focusedTextColor = MaterialTheme.colorScheme.secondary,
    )
}
