package br.com.android.buscamed.presentation.core.components.topbar

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import br.com.android.buscamed.presentation.core.theme.TopAppBarSubtitleTextStyle
import br.com.android.buscamed.presentation.core.theme.TopAppBarTitleTextStyle

/**
 * Implementação simplificada da TopAppBar com suporte a título e subtítulo.
 *
 * Esta função facilita a criação de barras superiores comuns, organizando o título e o subtítulo
 * em uma coluna vertical e repassando as demais configurações para a [BaseTopAppBar].
 *
 * @param title Texto principal da barra superior.
 * @param subtitle Texto secundário opcional exibido abaixo do título.
 * @param onBackClick Função callback executada ao clicar no ícone de navegação.
 * @param actions Bloco Composable para definir ações personalizadas no lado direito.
 * @param menuItems Bloco Composable para definir itens de menu suspenso.
 * @param colors Configuração de cores para a barra superior.
 * @param showNavigationIcon Define se o ícone de navegação (voltar) deve ser exibido.
 * @param customNavigationIcon Ícone de navegação personalizado (opcional).
 * @param showMenu Define se o botão de menu de opções deve ser exibido.
 * @param titleTextStyle Estilo de texto para o título.
 * @param subtitleTextStyle Estilo de texto para o subtítulo.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar(
    title: String,
    subtitle: String? = null,
    onBackClick: () -> Unit = { },
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
    titleTextStyle: TextStyle = TopAppBarTitleTextStyle,
    subtitleTextStyle: TextStyle = TopAppBarSubtitleTextStyle
) {
    BaseTopAppBar(
        title = {
            Column {
                Text(
                    text = title,
                    style = titleTextStyle,
                )

                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = subtitleTextStyle,
                    )
                }
            }
        },
        colors = colors,
        actions = actions,
        menuItems = menuItems,
        showNavigationIcon = showNavigationIcon,
        customNavigationIcon = customNavigationIcon,
        onBackClick = onBackClick,
        showMenu = showMenu
    )
}
