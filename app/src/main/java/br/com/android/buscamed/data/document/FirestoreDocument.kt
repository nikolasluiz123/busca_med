package br.com.android.buscamed.data.document

import br.com.android.buscamed.data.gson.defaultGSon
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

/**
 * Representação abstrata de um documento do Firestore.
 *
 * Esta classe fornece a base para todos os documentos da camada de dados,
 * garantindo que cada documento possua um identificador único e suporte
 * a conversão para [Map], necessária para operações de escrita no Firestore.
 */
abstract class FirestoreDocument {
    /**
     * Identificador único do documento no Firestore.
     */
    abstract val id: String?

    /**
     * Converte o objeto atual em um [Map] de chave-valor.
     *
     * Utiliza a biblioteca Gson para serializar o objeto e desserializá-lo como um mapa,
     * facilitando o envio de dados para as coleções do Firebase.
     *
     * @return Um [Map] contendo os campos do objeto e seus respectivos valores.
     */
    fun toMap(): Map<String, Any?> {
        val gson = GsonBuilder().defaultGSon()
        val json = gson.toJson(this)
        return gson.fromJson(json, object : TypeToken<Map<String, Any?>>() {}.type)
    }
}
