package br.com.android.buscamed.presentation.core.extensions

import br.com.android.buscamed.data.gson.defaultGSon
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Remove o primeiro e o último caractere de uma [String].
 * Útil para limpar strings JSON que vêm encapsuladas por algum motivo.
 *
 * @receiver A string JSON a ser formatada.
 * @return A string sem o primeiro e último caractere.
 */
private fun String.formatJsonNavParam() = this.substring(1, this.length - 1)

/**
 * Converte uma string JSON, recebida como parâmetro de navegação, em um objeto do tipo `ARG`.
 *
 * @receiver A string JSON a ser convertida.
 * @param clazz A classe do tipo de objeto para a qual a string será convertida.
 * @param gson Uma instância opcional do [Gson] para ser usada na desserialização.
 * @return O objeto do tipo `ARG` populado com os dados do JSON.
 * @param <ARG> O tipo genérico do objeto de destino.
 */
fun <ARG> String.fromJsonNavParamToArgs(clazz: Class<ARG>, gson: Gson = GsonBuilder().defaultGSon()): ARG {
    return gson.getAdapter(clazz).fromJson(this.formatJsonNavParam())
}