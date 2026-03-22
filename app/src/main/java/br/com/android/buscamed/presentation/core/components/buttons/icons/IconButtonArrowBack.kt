package br.com.android.buscamed.presentation.core.components.buttons.icons

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import br.com.android.buscamed.R

@Composable
fun IconButtonArrowBack(
    modifier: Modifier = Modifier,
    iconColor: Color = MaterialTheme.colorScheme.onPrimary,
    enabled: Boolean = true,
    contentDescriptionResId: Int? = R.string.label_back,
    onClick: () -> Unit = { }
) {
    BaseIconButton(
        modifier = modifier,
        resId = R.drawable.ic_arrow_back_24dp,
        iconColor = iconColor,
        enabled = enabled,
        contentDescriptionResId = contentDescriptionResId,
        onClick = onClick
    )
}