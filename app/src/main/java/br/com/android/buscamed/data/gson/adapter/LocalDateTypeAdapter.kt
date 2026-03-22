package br.com.android.buscamed.data.gson.adapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * [JsonSerializer] e [JsonDeserializer] para o tipo [LocalDate] do Java Time.
 *
 * Este adapter garante que objetos [LocalDate] sejam convertidos para e de JSON
 * utilizando o formato padrão ISO_LOCAL_DATE (`AAAA-MM-DD`).
 *
 * - **Serialização**: Converte um objeto [LocalDate] em uma [String] como `"2023-10-27"`.
 * - **Desserialização**: Converte uma [String] como `"2023-10-27"` em um objeto [LocalDate].
 */
class LocalDateTypeAdapter : JsonSerializer<LocalDate?>, JsonDeserializer<LocalDate?> {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    override fun serialize(
        src: LocalDate?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(formatter.format(src))
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDate? {
        return LocalDate.parse(json?.asString, formatter)
    }
}