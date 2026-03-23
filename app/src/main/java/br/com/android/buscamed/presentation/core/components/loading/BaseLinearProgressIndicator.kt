package br.com.android.buscamed.presentation.core.components.loading

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import br.com.android.buscamed.presentation.core.tags.GenericUIComponentTag

@Composable
fun BaseLinearProgressIndicator(show: Boolean, modifier: Modifier = Modifier) {
    if (show) {
        LinearProgressIndicator(
            modifier = modifier
                .fillMaxWidth()
                .testTag(GenericUIComponentTag.LOADING_INDICATOR.name),
            color = MaterialTheme.colorScheme.primary
        )
    }
}