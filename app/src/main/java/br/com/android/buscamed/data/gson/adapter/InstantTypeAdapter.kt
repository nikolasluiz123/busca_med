package br.com.android.buscamed.data.gson.adapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.Instant

/**
 * [JsonSerializer] e [JsonDeserializer] para o tipo [Instant] do Java Time.
 *
 * Este adapter utiliza a representação de [String] padrão do [Instant], que está
 * no formato UTC, como `2023-10-27T17:30:55.123Z`.
 *
 * - **Serialização**: Converte um objeto [Instant] em sua representação `toString()`.
 * - **Desserialização**: Faz o parse de uma [String] para um objeto [Instant].
 */
class InstantTypeAdapter : JsonSerializer<Instant>, JsonDeserializer<Instant> {

    override fun serialize(
        src: Instant?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src?.toString())
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Instant? {
        return json?.asString?.let { Instant.parse(it) }
    }
}