package br.dev.schirmer.utils.kotlin.json.clazz

import br.dev.schirmer.utils.kotlin.json.ExportOptionsInclude
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.text.SimpleDateFormat


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