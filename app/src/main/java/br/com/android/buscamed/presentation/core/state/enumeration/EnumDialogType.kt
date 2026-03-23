package br.com.android.buscamed.presentation.core.state.enumeration

import br.com.android.buscamed.R

/**
 * Define os tipos de diálogos disponíveis na interface do usuário.
 *
 * Cada tipo está associado a um recurso de string para o título,
 * permitindo padronizar a identidade visual de erros, confirmações e informações.
 *
 * @property titleResId Identificador do recurso de string para o título do diálogo.
 */
enum class EnumDialogType(val titleResId: Int) {
    /** Tipo utilizado para mensagens de erro. */
    ERROR(R.string.error_dialog_title),
    /** Tipo utilizado para pedidos de confirmação de ações. */
    CONFIRMATION(R.string.warning_dialog_title),
    /** Tipo utilizado para mensagens informativas gerais. */
    INFORMATION(R.string.information_dialog_title)
}
