package br.com.android.buscamed.presentation.core.components.buttons.icons

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import br.com.android.buscamed.R

/**
 * Botão de ícone padrão para navegação de retorno (voltar).
 *
 * Utiliza o ícone de seta para a esquerda e define uma descrição de acessibilidade padrão
 * ("Voltar"). É comumente utilizado em TopAppBars.
 *
 * @param modifier Modificador de layout.
 * @param iconColor Cor aplicada ao ícone.
 * @param enabled Define se o botão está ativo.
 * @param contentDescriptionResId Recurso de string para acessibilidade.
 * @param onClick Função callback executada ao clicar para voltar.
 */
@Composable
fun IconButtonArrowBack(
    modifier: Modifier = Modifier,
    iconColor: Color = MaterialTheme.colorScheme.onPrimary,
    enabled: Boolean = true,
    contentDescriptionResId: Int? = R.string.label_back,
    onClick: () -> Unit = { }
) {
    BaseIconButton(
        modifier = modifier,
        resId = R.drawable.ic_arrow_back_24dp,
        iconColor = iconColor,
        enabled = enabled,
        contentDescriptionResId = contentDescriptionResId,
        onClick = onClick
    )
}
