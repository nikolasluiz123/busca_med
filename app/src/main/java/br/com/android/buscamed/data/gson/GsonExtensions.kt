package br.com.android.buscamed.data.gson

import br.com.android.buscamed.data.gson.adapter.InstantTypeAdapter
import br.com.android.buscamed.data.gson.adapter.LocalDateTimeTypeAdapter
import br.com.android.buscamed.data.gson.adapter.LocalDateTypeAdapter
import br.com.android.buscamed.data.gson.adapter.LocalTimeTypeAdapter
import br.com.android.buscamed.data.gson.adapter.OffsetDateTimeTypeAdapter
import br.com.android.buscamed.data.gson.adapter.ZoneOffsetTypeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneOffset

fun GsonBuilder.defaultGSon(serializeNulls: Boolean = false): Gson {
    val builder = this.registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeTypeAdapter())
        .registerTypeAdapter(LocalDate::class.java, LocalDateTypeAdapter())
        .registerTypeAdapter(LocalTime::class.java, LocalTimeTypeAdapter())
        .registerTypeAdapter(OffsetDateTime::class.java, OffsetDateTimeTypeAdapter())
        .registerTypeAdapter(Instant::class.java, InstantTypeAdapter())
        .registerTypeAdapter(ZoneOffset::class.java, ZoneOffsetTypeAdapter())

    if (serializeNulls) {
        builder.serializeNulls()
    }

    return builder.create()
}