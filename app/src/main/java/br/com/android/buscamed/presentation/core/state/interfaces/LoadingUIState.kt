package br.com.android.buscamed.presentation.core.state.interfaces

/**
 * Define o estado de carregamento de uma tela.
 *
 * Esta interface deve ser implementada por estados de UI que precisam
 * controlar a visibilidade de indicadores de progresso.
 *
 * @property showLoading Indica se o componente de carregamento deve estar visível.
 * @property onToggleLoading Função callback para alternar o estado de carregamento.
 */
interface LoadingUIState {
    val showLoading: Boolean
    val onToggleLoading: () -> Unit
}
