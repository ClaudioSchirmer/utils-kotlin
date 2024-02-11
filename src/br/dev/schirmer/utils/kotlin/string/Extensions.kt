package br.dev.schirmer.utils.kotlin.string

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.text.Normalizer

inline fun <reified T : Any> String.onlyDigits(): T = this.filter { it.isDigit() }.let {
    return when (T::class) {
        String::class -> it as T
        Int::class -> (if(it.isNotBlank()) it.toInt() else 0) as T
        Long::class -> (if(it.isNotBlank()) it.toLong() else 0L) as T
        Double::class -> (if (it.isNotBlank()) it.toDouble() else 0.0) as T
        else -> throw Throwable("String.onlyDigits() only supports return types such as String, Int, Long, or Double!")
    }
}

fun String.removeDiacritics(): String =
    Regex("\\p{InCombiningDiacriticalMarks}+").replace(Normalizer.normalize(this, Normalizer.Form.NFD), "")

inline fun <reified TObject : Any> String.toClass(): TObject {
    return try {
        jacksonObjectMapper()
            .registerKotlinModule()
            .registerModule(JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .readValue(this, TObject::class.java)
    } catch (e: Exception) {
        throw Exception("Json cannot be converted to class.", e)
    }
}