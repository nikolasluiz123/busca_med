package br.com.android.buscamed.data.mapper

import android.graphics.Rect
import br.com.android.buscamed.domain.model.capture.BoundingBox
import br.com.android.buscamed.domain.model.capture.TextBlock
import br.com.android.buscamed.domain.model.capture.TextElement
import br.com.android.buscamed.domain.model.capture.TextLine
import br.com.android.buscamed.domain.model.capture.TextResult
import br.com.android.buscamed.domain.model.capture.TextSymbol
import com.google.mlkit.vision.text.Text

/**
 * Converte um objeto [Rect] nativo do Android para a entidade de domínio [BoundingBox].
 *
 * @receiver O retângulo original proveniente do ML Kit.
 * @return A representação no domínio das coordenadas espaciais.
 */
fun Rect?.toDomain(): BoundingBox? {
    this ?: return null
    return BoundingBox(
        left = left.toFloat(),
        top = top.toFloat(),
        right = right.toFloat(),
        bottom = bottom.toFloat()
    )
}

/**
 * Converte a entidade raiz [Text] do ML Kit para a entidade de domínio [TextResult].
 *
 * @receiver O resultado completo da extração do ML Kit.
 * @return A raiz da árvore de texto no modelo de domínio.
 */
fun Text.toDomain(): TextResult {
    return TextResult(
        text = text,
        blocks = textBlocks.map { it.toDomain() }
    )
}

/**
 * Converte um [Text.TextBlock] do ML Kit para a entidade de domínio [TextBlock].
 *
 * @receiver O bloco de texto original.
 * @return O bloco de texto mapeado para o domínio.
 */
fun Text.TextBlock.toDomain(): TextBlock {
    return TextBlock(
        text = text,
        boundingBox = boundingBox.toDomain(),
        lines = lines.map { it.toDomain() }
    )
}

/**
 * Converte uma [Text.Line] do ML Kit para a entidade de domínio [TextLine].
 *
 * @receiver A linha de texto original.
 * @return A linha de texto mapeada para o domínio.
 */
fun Text.Line.toDomain(): TextLine {
    return TextLine(
        text = text,
        boundingBox = boundingBox.toDomain(),
        confidence = confidence,
        elements = elements.map { it.toDomain() }
    )
}

/**
 * Converte um [Text.Element] do ML Kit para a entidade de domínio [TextElement].
 *
 * @receiver O elemento de texto (palavra) original.
 * @return O elemento de texto mapeado para o domínio.
 */
fun Text.Element.toDomain(): TextElement {
    return TextElement(
        text = text,
        boundingBox = boundingBox.toDomain(),
        confidence = confidence,
        symbols = symbols.map { it.toDomain() }
    )
}

/**
 * Converte um [Text.Symbol] do ML Kit para a entidade de domínio [TextSymbol].
 *
 * @receiver O símbolo (caractere) original.
 * @return O símbolo mapeado para o domínio.
 */
fun Text.Symbol.toDomain(): TextSymbol {
    return TextSymbol(
        text = text,
        boundingBox = boundingBox.toDomain(),
        confidence = confidence
    )
}