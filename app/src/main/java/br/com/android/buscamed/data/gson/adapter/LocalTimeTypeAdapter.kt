package br.com.android.buscamed.data.gson.adapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * [JsonSerializer] e [JsonDeserializer] para o tipo [LocalTime] do Java Time.
 *
 * Este adapter garante que objetos [LocalTime] sejam convertidos para e de JSON
 * utilizando o formato padrão ISO_LOCAL_TIME (`HH:mm:ss`).
 *
 * - **Serialização**: Converte um objeto [LocalTime] em uma [String] como `"14:30:55"`.
 * - **Desserialização**: Converte uma [String] como `"14:30:55"` em um objeto [LocalTime].
 */
class LocalTimeTypeAdapter : JsonSerializer<LocalTime?>, JsonDeserializer<LocalTime?> {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME

    override fun serialize(
        src: LocalTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src?.format(formatter))
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalTime? {
        return LocalTime.parse(json?.asString, formatter)
    }
}