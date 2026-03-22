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