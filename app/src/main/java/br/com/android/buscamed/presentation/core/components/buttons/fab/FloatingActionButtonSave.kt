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
 * FAB que representa a ação de salvar.
 *
 * @param modifier O modificador para aplicar ao componente.
 * @param onClick A ação a ser realizada quando o botão é clicado.
 * @uthor Nikolas Luiz Schmitt
 */
@Composable
fun FloatingActionButtonSave(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconColor: Color = MaterialTheme.colorScheme.onPrimary,
    containerColor: Color = MaterialTheme.colorScheme.primary
) {
    BaseFloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        containerColor = containerColor
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_check_24dp),
            contentDescription = stringResource(R.string.label_save),
            tint = iconColor
        )
    }
}



@Preview(device = "id:small_phone", apiLevel = 35)
@Composable
fun FloatingActionButtonSavePreviewDark() {
    BuscaMedTheme(darkTheme = true) {
        Surface {
            FloatingActionButtonSave(
                onClick = { }
            )
        }
    }
}

@Preview(device = "id:small_phone", apiLevel = 35)
@Composable
fun FloatingActionButtonSavePreviewLight() {
    BuscaMedTheme(darkTheme = false) {
        Surface {
            FloatingActionButtonSave(
                onClick = { }
            )
        }
    }
}
