package br.com.android.buscamed.presentation.core.components.buttons.rounded

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.android.buscamed.R

/**
 * Botão arredondado estilizado para autenticação via Google.
 *
 * Esta função cria um botão de ícone com o logotipo do Google, seguindo a identidade visual
 * para provedores de autenticação social.
 *
 * @param modifier Modificador de layout para o botão.
 * @param enabled Define se o botão está ativo para interação.
 * @param colors Configuração de cores para os estados do botão.
 * @param onClick Função callback executada ao clicar no botão.
 */
@Composable
fun RoundedGoogleButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
        disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
    ),
    onClick: () -> Unit = { }
) {
    IconButton(
        modifier = modifier.size(48.dp),
        onClick = onClick,
        colors = colors,
        enabled = enabled
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_google),
            contentDescription = stringResource(R.string.rounded_button_google_content_description),
        )
    }
}
