package br.com.android.buscamed.domain.exception

/**
 * Exceção lançada quando o serviço remoto retorna um erro mapeado.
 *
 * @property errorCode Código interno do erro.
 * @property message Mensagem descritiva do erro.
 * @property traceId Identificador de rastreamento para debug.
 */
class ServiceException(
    val errorCode: String,
    override val message: String,
    val traceId: String? = null
) : Exception(message)