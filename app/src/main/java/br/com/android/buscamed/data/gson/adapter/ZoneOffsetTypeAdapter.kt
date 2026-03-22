package br.com.android.buscamed.data.gson.adapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.ZoneOffset

/**
 * [JsonSerializer] e [JsonDeserializer] para o tipo [ZoneOffset] do Java Time.
 *
 * Este adapter utiliza a representação de [String] padrão do [ZoneOffset],
 * como `Z` (para UTC) ou `-03:00`.
 *
 * - **Serialização**: Converte um objeto [ZoneOffset] em sua representação `toString()`.
 * - **Desserialização**: Faz o parse de uma [String] para um objeto [ZoneOffset].
 */
class ZoneOffsetTypeAdapter : JsonSerializer<ZoneOffset>, JsonDeserializer<ZoneOffset> {

    override fun serialize(
        src: ZoneOffset?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src?.toString())
    }



    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ZoneOffset? {
        return json?.asString?.let { ZoneOffset.of(it) }
    }
}