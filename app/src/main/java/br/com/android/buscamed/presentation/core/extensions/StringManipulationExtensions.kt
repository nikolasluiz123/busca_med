package br.com.android.buscamed.presentation.core.extensions

import java.text.DecimalFormat

fun String.parseDouble(): Double? {
    return try {
        DecimalFormat.getInstance().parse(this)?.toDouble()
    } catch (_: Exception) {
        null
    }
}