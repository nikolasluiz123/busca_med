package br.com.android.buscamed.data.gson.adapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * [JsonSerializer] e [JsonDeserializer] para o tipo [LocalDateTime] do Java Time.
 *
 * Este adapter garante que objetos [LocalDateTime] sejam convertidos para e de JSON
 * utilizando o formato padrão ISO_LOCAL_DATE_TIME (`AAAA-MM-DDTHH:mm:ss`).
 * Também trata corretamente valores nulos.
 *
 * - **Serialização**: Converte um [LocalDateTime] em `"2023-10-27T14:30:55"`. Se o objeto for `null`, retorna `JsonNull`.
 * - **Desserialização**: Converte `"2023-10-27T14:30:55"` em um [LocalDateTime]. Se o JSON for `null`, retorna `null`.
 */
class LocalDateTimeTypeAdapter : JsonSerializer<LocalDateTime?>, JsonDeserializer<LocalDateTime?> {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    override fun serialize(
        src: LocalDateTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return if (src == null) {
            JsonNull.INSTANCE
        } else {
            JsonPrimitive(src.format(formatter))
        }
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDateTime? {
        return if (json == null || json.isJsonNull) {
            null
        } else {
            LocalDateTime.parse(json.asString, formatter)
        }
    }
}