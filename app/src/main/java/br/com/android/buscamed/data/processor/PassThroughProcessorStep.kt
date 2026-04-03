package br.com.android.buscamed.data.processor

import br.com.android.buscamed.domain.processor.ImageProcessorStep
import java.io.File

/**
 * Passo de processamento que atua como um repassador passivo, sem aplicar alterações à imagem.
 */
class PassThroughProcessorStep : ImageProcessorStep {
    override fun process(image: File): File {
        return image
    }
}