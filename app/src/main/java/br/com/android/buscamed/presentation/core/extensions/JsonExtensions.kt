package br.com.android.buscamed.presentation.core.extensions

import br.com.android.buscamed.data.gson.defaultGSon
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Remove o primeiro e o último caractere de uma [String].
 * 
 * Função utilitária interna para limpar delimitadores de strings JSON.
 *
 * @return A string formatada.
 */
private fun String.formatJsonNavParam() = this.substring(1, this.length - 1)

/**
 * Converte uma string JSON, recebida como parâmetro de navegação, em um objeto de dados.
 *
 * Esta função prepara a string e utiliza o Gson para realizar a desserialização para o tipo [ARG] especificado.
 *
 * @param clazz A classe do tipo de objeto para a qual a string será convertida.
 * @param gson Uma instância do [Gson] para ser usada na desserialização (padrão configurado com adaptadores do projeto).
 * @return O objeto do tipo [ARG] populado com os dados do JSON.
 */
fun <ARG> String.fromJsonNavParamToArgs(clazz: Class<ARG>, gson: Gson = GsonBuilder().defaultGSon()): ARG {
    return gson.getAdapter(clazz).fromJson(this.formatJsonNavParam())
}
