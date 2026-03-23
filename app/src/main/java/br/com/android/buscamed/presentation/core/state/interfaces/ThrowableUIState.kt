package br.com.android.buscamed.presentation.core.state.interfaces

import br.com.android.buscamed.presentation.core.state.dialog.MessageDialogState

/**
 * Define o estado para tratamento e exibição visual de erros na interface do usuário.
 *
 * Esta interface deve ser implementada por estados de UI que necessitam reportar
 * falhas e exceções através de diálogos de mensagem padronizados.
 *
 * @property messageDialogState O estado do diálogo de mensagem utilizado para exibir informações de erro.
 */
interface ThrowableUIState {
    val messageDialogState: MessageDialogState
}
