package br.dev.schirmer.utils.kotlin.json

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.text.SimpleDateFormat


object JsonUtils {
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

    inline fun <reified TObject> TObject.toJson(
        writeNulls: Boolean = false,
        excludeFields: Set<String>? = null
    ): String {
        val jackson = jacksonObjectMapper()
            .registerKotlinModule()
            .registerModule(JavaTimeModule())
            .setDateFormat(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"))

        if (excludeFields != null) {
            jackson.addMixIn(TObject::class.java, PropertyFilterMixIn::class.java)
            jackson.setFilterProvider(
                SimpleFilterProvider().addFilter(
                    "custom_filters",
                    SimpleBeanPropertyFilter.serializeAllExcept(excludeFields)
                )
            )
        }

        if (!writeNulls) {
            jackson.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        }

        return jackson.writeValueAsString(this)
    }

    @JsonFilter("custom_filters")
    class PropertyFilterMixIn
}