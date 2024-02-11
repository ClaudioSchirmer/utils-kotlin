package br.dev.schirmer.utils.kotlin.json.string

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

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