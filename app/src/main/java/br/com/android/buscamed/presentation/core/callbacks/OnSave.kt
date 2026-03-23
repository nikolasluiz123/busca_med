package br.com.android.buscamed.presentation.core.callbacks

/**
 * Interface funcional para operações de salvamento.
 *
 * Define um contrato simples para executar uma ação de persistência,
 * provendo callbacks para os estados de sucesso e falha.
 */
fun interface OnSave {
    /**
     * Executa a operação de salvamento.
     *
     * @param onSuccess Função a ser chamada em caso de êxito.
     * @param onFailure Função a ser chamada em caso de erro.
     */
    fun execute(onSuccess: () -> Unit, onFailure: () -> Unit)
}
