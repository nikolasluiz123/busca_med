package br.com.android.buscamed.presentation.core.components.loading

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import br.com.android.buscamed.presentation.core.tags.GenericUIComponentTag

/**
 * Indicador de progresso linear padronizado.
 *
 * Exibe uma barra de progresso horizontal que ocupa toda a largura disponível.
 * Utilizado para indicar carregamentos que não bloqueiam a interface completa.
 *
 * @param show Controla a visibilidade da barra de progresso.
 * @param modifier Modificador de layout.
 */
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
