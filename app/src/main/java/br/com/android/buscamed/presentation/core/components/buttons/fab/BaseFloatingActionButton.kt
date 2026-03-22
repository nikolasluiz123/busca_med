package br.com.android.buscamed.presentation.core.components.buttons.fab

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Um [FloatingActionButton] (FAB) padrão para o aplicativo, que encapsula o
 * [FloatingActionButton] do Material Design com estilos predefinidos.
 *
 * @param modifier O [Modifier] a ser aplicado ao FAB.
 * @param containerColor A cor de fundo do botão. O padrão é a cor primária do tema.
 * @param onClick A ação a ser realizada quando o botão é clicado.
 * @param content O conteúdo a ser exibido dentro do FAB, geralmente um [Icon].
 *
 * @author Nikolas Luiz Schmitt
 */
@Composable
fun BaseFloatingActionButton(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit = { },
    content: @Composable () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        containerColor = containerColor,
        onClick = onClick
    ) {
        content()
    }
}