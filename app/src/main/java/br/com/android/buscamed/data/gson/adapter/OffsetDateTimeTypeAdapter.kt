package br.com.android.buscamed.data.gson.adapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

/**
 * [JsonSerializer] e [JsonDeserializer] para o tipo [OffsetDateTime] do Java Time.
 *
 * Este adapter garante que objetos [OffsetDateTime] sejam convertidos para e de JSON
 * utilizando o formato padrão ISO_OFFSET_DATE_TIME (`AAAA-MM-DDTHH:mm:ss±HH:mm`).
 *
 * - **Serialização**: Converte um [OffsetDateTime] em uma [String] como `"2023-10-27T14:30:55-03:00"`.
 * - **Desserialização**: Converte uma [String] como `"2023-10-27T14:30:55-03:00"` em um [OffsetDateTime].
 */
class OffsetDateTimeTypeAdapter : JsonSerializer<OffsetDateTime?>, JsonDeserializer<OffsetDateTime?> {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    override fun serialize(
        src: OffsetDateTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src?.format(formatter))
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): OffsetDateTime? {
        return OffsetDateTime.parse(json?.asString, formatter)
    }
}