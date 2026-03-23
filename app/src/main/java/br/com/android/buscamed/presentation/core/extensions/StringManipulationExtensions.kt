package br.com.android.buscamed.presentation.core.extensions

import java.text.DecimalFormat

/**
 * Converte uma [String] em um valor [Double] utilizando a formatação local do sistema.
 *
 * Esta função tenta realizar o parse da string para um número decimal, tratando
 * possíveis exceções de formatação e retornando nulo caso a conversão não seja possível.
 *
 * @return O valor convertido para [Double] ou null em caso de erro no parse.
 */
fun String.parseDouble(): Double? {
    return try {
        DecimalFormat.getInstance().parse(this)?.toDouble()
    } catch (_: Exception) {
        null
    }
}
