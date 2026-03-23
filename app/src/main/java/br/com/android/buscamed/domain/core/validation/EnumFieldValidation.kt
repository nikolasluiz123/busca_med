package br.com.android.buscamed.domain.core.validation

/**
 * Interface utilizada por enumeradores que definem as regras de validação para campos das classes de domínio.
 *
 * Provê os metadados necessários para a execução das regras de negócio, permitindo expandir as validações
 * para além de restrições de tamanho, conforme a necessidade de cada campo do domínio.
 */
interface EnumFieldValidation {
    /**
     * Comprimento máximo permitido para o campo.
     */
    val maxLength: Int

    /**
     * Comprimento mínimo permitido para o campo.
     */
    val minLength: Int
}
