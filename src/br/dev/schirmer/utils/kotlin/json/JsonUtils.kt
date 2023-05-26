package br.dev.schirmer.utils.kotlin.json

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.json.JsonMapper
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
        alphabeticalOrder: Boolean = true,
        exportOptionsInclude: ExportOptionsInclude = ExportOptionsInclude.NON_EMPTY
    ): String {
        val jackson = JsonMapper
            .builder()
            .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, alphabeticalOrder)
            .build()

        jackson
            .registerKotlinModule()
            .registerModule(JavaTimeModule())
            .setDateFormat(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"))

        when (exportOptionsInclude) {
            ExportOptionsInclude.NON_EMPTY -> jackson.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
            ExportOptionsInclude.NON_NULL -> jackson.setSerializationInclusion(JsonInclude.Include.NON_NULL)
            else -> {}
        }

        return jackson.writeValueAsString(this)
    }

    enum class ExportOptionsInclude {
        NON_EMPTY,
        NON_NULL,
        ALWAYS
    }
}