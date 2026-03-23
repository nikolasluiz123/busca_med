package br.com.android.buscamed.presentation.core.state.interfaces.field

/**
 * Interface base que define o comportamento de um campo de texto na interface do usuário.
 *
 * Padroniza as propriedades necessárias para gerenciar o estado e a validação
 * de campos de entrada de dados de forma reativa.
 *
 * @property value O texto atual contido no campo.
 * @property onChange Função callback disparada sempre que o valor do campo é alterado.
 * @property errorMessage Mensagem de erro a ser exibida caso a validação do campo falhe.
 */
interface TextField {
    val value: String
    val onChange: (String) -> Unit
    val errorMessage: String
}
