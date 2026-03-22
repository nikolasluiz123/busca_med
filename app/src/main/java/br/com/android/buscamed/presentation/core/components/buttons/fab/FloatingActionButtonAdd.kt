package br.com.android.buscamed.presentation.core.components.buttons.fab

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.android.buscamed.R
import br.com.android.buscamed.presentation.core.theme.BuscaMedTheme

/**
 * FAB que representa a ação de adicionar.
 *
 * @param modifier O modificador para aplicar ao componente.
 * @param onClick A ação a ser realizada quando o botão é clicado.
 * @author Nikolas Luiz Schmitt
 */
@Composable
fun FloatingActionButtonAdd(
    modifier: Modifier = Modifier,
    iconColor: Color = MaterialTheme.colorScheme.onPrimary,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit = { }
) {
    BaseFloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        containerColor = containerColor
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add_24dp),
            contentDescription = stringResource(R.string.label_add),
            tint = iconColor
        )
    }
}

@Preview(device = "id:small_phone", apiLevel = 35)
@Composable
fun FloatingActionButtonAddPreviewDark() {
    BuscaMedTheme(darkTheme = true) {
        Surface {
            FloatingActionButtonAdd()
        }
    }
}

@Preview(device = "id:small_phone", apiLevel = 35)
@Composable
fun FloatingActionButtonAddPreviewLight() {
    BuscaMedTheme(darkTheme = false) {
        Surface {
            FloatingActionButtonAdd()
        }
    }
}