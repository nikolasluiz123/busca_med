package br.com.android.buscamed.presentation.core.components.topbar

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import br.com.android.buscamed.presentation.core.components.buttons.icons.IconButtonArrowBack
import br.com.android.buscamed.presentation.core.components.buttons.icons.MenuIconButton

/**
 * Componente base para a barra superior (TopAppBar) do sistema.
 *
 * Esta função provê uma estrutura flexível para a barra de navegação superior, permitindo
 * configurar o título, ações, ícones de navegação e menus de opções de forma padronizada.
 *
 * @param title Função Composable que define o conteúdo do título.
 * @param onBackClick Função callback executada ao clicar no ícone de navegação padrão (voltar).
 * @param actions Bloco Composable para definir ações personalizadas no lado direito da barra.
 * @param menuItems Bloco Composable para definir os itens de um menu suspenso.
 * @param colors Configuração de cores para os diferentes elementos da barra superior.
 * @param showNavigationIcon Define se o ícone de navegação deve ser exibido.
 * @param customNavigationIcon Permite fornecer um Composable customizado para o ícone de navegação.
 * @param showMenu Define se o botão de menu de opções deve ser exibido.
 * @param windowInsets Define as margens de janela (insets) a serem respeitadas pela barra.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseTopAppBar(
    title: @Composable () -> Unit,
    onBackClick: () -> Unit,
    actions: @Composable () -> Unit = { },
    menuItems: @Composable () -> Unit = { },
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.onPrimary,
        actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
    ),
    showNavigationIcon: Boolean = true,
    customNavigationIcon: (@Composable () -> Unit)? = null,
    showMenu: Boolean = false,
    windowInsets: WindowInsets = WindowInsets(0.dp),
) {
    TopAppBar(
        title = title,
        colors = colors,
        windowInsets = windowInsets,
        navigationIcon = {
            if (showNavigationIcon) {
                if (customNavigationIcon != null) {
                    customNavigationIcon()
                } else {
                    IconButtonArrowBack(
                        onClick = onBackClick,
                    )
                }
            }
        },
        actions = {
            actions()

            if (showMenu) {
                MenuIconButton(menuItems)
            }
        }
    )
}
