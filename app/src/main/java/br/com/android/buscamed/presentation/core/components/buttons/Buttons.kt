package br.com.android.buscamed.presentation.core.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import br.com.android.buscamed.presentation.core.theme.ButtonTextStyle
import br.com.android.buscamed.presentation.core.theme.TextButtonTextStyle

/**
 * Botão preenchido padrão utilizado para ações principais.
 *
 * @param label Texto exibido no botão.
 * @param onClickListener Função callback executada ao clicar no botão.
 * @param modifier Modificador de layout.
 * @param enabled Define se o botão está habilitado para interação.
 * @param textStyle Estilo de texto a ser aplicado ao rótulo.
 * @param colors Configuração de cores para os estados do botão.
 */
@Composable
fun BaseButton(
    label: String,
    onClickListener: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textStyle: TextStyle = ButtonTextStyle,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
        disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
    )
) {
    Button(
        modifier = modifier,
        onClick = onClickListener,
        enabled = enabled,
        colors = colors
    ) {
        Text(
            text = label,
            style = textStyle,
        )
    }
}

/**
 * Botão com contorno (outlined) utilizado para ações secundárias.
 *
 * @param label Texto exibido no botão.
 * @param onClickListener Função callback executada ao clicar no botão.
 * @param modifier Modificador de layout.
 * @param enabled Define se o botão está habilitado.
 * @param textStyle Estilo de texto a ser aplicado ao rótulo.
 * @param border Configuração da borda do botão.
 */
@Composable
fun BaseOutlinedButton(
    label: String,
    onClickListener: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textStyle: TextStyle = ButtonTextStyle,
    border: BorderStroke = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
) {
    OutlinedButton(
        modifier = modifier,
        border = border,
        onClick = onClickListener,
        enabled = enabled,
    ) {
        Text(
            text = label,
            style = textStyle
        )
    }
}

/**
 * Botão de texto simples utilizado para ações de menor destaque.
 *
 * @param label Texto exibido no botão.
 * @param onClickListener Função callback executada ao clicar no botão.
 * @param modifier Modificador de layout.
 * @param colors Configuração de cores do botão.
 * @param enabled Define se o botão está habilitado.
 * @param textStyle Estilo de texto a ser aplicado ao rótulo.
 */
@Composable
fun BaseTextButton(
    label: String,
    onClickListener: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    enabled: Boolean = true,
    textStyle: TextStyle = TextButtonTextStyle
) {
    TextButton(
        modifier = modifier,
        onClick = onClickListener,
        enabled = enabled,
        colors = colors
    ) {
        Text(text = label, style = textStyle)
    }
}

/**
 * Botão de texto específico para diálogos de sistema.
 *
 * @param labelResId Identificador do recurso de string para o rótulo.
 * @param onClick Função callback executada ao clicar no botão.
 * @param modifier Modificador de layout.
 * @param colors Configuração de cores do botão.
 */
@Composable
fun DefaultDialogTextButton(
    labelResId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.textButtonColors(
        contentColor = MaterialTheme.colorScheme.onSurface,
        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
    )
) {
    TextButton(
        modifier = modifier,
        colors = colors,
        onClick = {
            onClick()
        }
    ) {
        Text(text = stringResource(id = labelResId))
    }
}
