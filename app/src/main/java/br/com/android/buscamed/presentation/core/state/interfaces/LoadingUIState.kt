package br.com.android.buscamed.presentation.core.state.interfaces

interface LoadingUIState {
    val showLoading: Boolean
    val onToggleLoading: () -> Unit
}