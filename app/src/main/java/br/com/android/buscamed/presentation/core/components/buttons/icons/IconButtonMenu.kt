package br.com.android.buscamed.presentation.core.components.buttons.icons

import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/**
 * Componente de botão de ícone que exibe um menu suspenso (DropdownMenu).
 *
 * Esta função gerencia o estado de visibilidade do menu e exibe o ícone de "mais opções" (MoreVert).
 * Ao ser clicado, o menu é alternado.
 *
 * @param menuItems Função Composable que define os itens a serem exibidos dentro do menu.
 */
@Composable
fun MenuIconButton(
    menuItems: @Composable () -> Unit = { }
) {
    var showMenu by remember { mutableStateOf(false) }

    IconButtonMoreVert { showMenu = !showMenu }

    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
        menuItems()
    }
}
