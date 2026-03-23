package br.com.android.buscamed.presentation.core.components.buttons.icons

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import kotlin.let

/**
 * Componente base de botão com ícone utilizando recursos de imagem.
 *
 * Esta função provê uma estrutura padronizada para botões que exibem ícones baseados em drawables,
 * garantindo consistência visual e de comportamento no aplicativo.
 *
 * @param resId Identificador do recurso de imagem (drawable).
 * @param iconColor Cor aplicada ao ícone.
 * @param modifier Modificador de layout para o botão.
 * @param iconModifier Modificador de layout específico para o ícone interno.
 * @param enabled Define se o botão está ativo para interação.
 * @param contentDescriptionResId Recurso de string para descrição de acessibilidade.
 * @param onClick Função callback executada ao clicar no botão.
 */
@Composable
fun BaseIconButton(
    resId: Int,
    modifier: Modifier = Modifier,
    iconColor: Color = MaterialTheme.colorScheme.onPrimary,
    iconModifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentDescriptionResId: Int? = null,
    onClick: () -> Unit = { }
) {
    IconButton(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = iconColor,
            disabledContentColor = iconColor.copy(alpha = 0.5f)
        )
    ) {
        Icon(
            modifier = iconModifier,
            painter = painterResource(id = resId),
            contentDescription = contentDescriptionResId?.let { stringResource(it) }
        )
    }
}

/**
 * Componente base de botão com ícone utilizando vetores (ImageVector).
 *
 * @param vector Vetor de imagem a ser exibido.
 * @param iconColor Cor aplicada ao ícone.
 * @param modifier Modificador de layout para o botão.
 * @param iconModifier Modificador de layout específico para o ícone interno.
 * @param enabled Define se o botão está ativo para interação.
 * @param contentDescriptionResId Recurso de string para descrição de acessibilidade.
 * @param onClick Função callback executada ao clicar no botão.
 */
@Composable
fun BaseIconButton(
    vector: ImageVector,
    modifier: Modifier = Modifier,
    iconColor: Color = MaterialTheme.colorScheme.onPrimary,
    iconModifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentDescriptionResId: Int? = null,
    onClick: () -> Unit = { }
) {
    IconButton(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = iconColor,
            disabledContentColor = iconColor.copy(alpha = 0.5f)
        )
    ) {
        Icon(
            modifier = iconModifier,
            imageVector = vector,
            contentDescription = contentDescriptionResId?.let { stringResource(it) }
        )
    }
}
