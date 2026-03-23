package br.com.android.buscamed.presentation.core.components.buttons.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

/**
 * Botão de ícone padrão para exibição de "mais opções" (três pontos verticais).
 *
 * @param onClick Função callback executada ao clicar no botão.
 */
@Composable
fun IconButtonMoreVert(onClick: () -> Unit = { }) {
    IconButton(onClick = onClick) {
        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
    }
}
