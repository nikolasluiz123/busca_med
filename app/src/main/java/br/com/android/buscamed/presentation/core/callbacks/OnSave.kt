package br.com.android.buscamed.presentation.core.callbacks

fun interface OnSave {
    fun execute(onSuccess: () -> Unit, onFailure: () -> Unit)
}