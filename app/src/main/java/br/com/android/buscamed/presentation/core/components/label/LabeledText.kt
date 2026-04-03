package br.com.android.buscamed.presentation.core.components.label

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.android.buscamed.presentation.core.theme.LabelTextStyle
import br.com.android.buscamed.presentation.core.theme.ValueTextStyle

/**
 * Um componente que exibe um par de textos, um "rótulo" e um "valor", empilhados verticalmente.
 *
 * @param modifier O [Modifier] a ser aplicado ao contêiner da [Column].
 * @param label O texto a ser exibido como rótulo (superior).
 * @param value O texto a ser exibido como valor (inferior).
 * @param maxLinesValue O número máximo de linhas para o texto de valor.
 * @param overflowValue Como o estouro de texto do valor deve ser tratado.
 * @param textAlign O alinhamento do texto para o rótulo e o valor.
 * @param labelStyle O estilo do texto a ser aplicado ao rótulo.
 * @param valueStyle O estilo do texto a ser aplicado ao valor.
 *
 * @author Nikolas Luiz Schmitt
 */
@Composable
fun LabeledText(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    maxLinesValue: Int = Int.MAX_VALUE,
    overflowValue: TextOverflow = TextOverflow.Ellipsis,
    textAlign: TextAlign? = null,
    labelStyle: TextStyle = LabelTextStyle,
    valueStyle: TextStyle = ValueTextStyle
) {
    Column(modifier.wrapContentHeight()) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = label,
            style = labelStyle,
            textAlign = textAlign
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            text = value,
            style = valueStyle,
            maxLines = maxLinesValue,
            overflow = overflowValue,
            textAlign = textAlign
        )
    }
}