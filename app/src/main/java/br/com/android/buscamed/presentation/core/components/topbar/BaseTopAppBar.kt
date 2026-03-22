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