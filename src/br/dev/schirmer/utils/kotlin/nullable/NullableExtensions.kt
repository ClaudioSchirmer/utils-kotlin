package br.dev.schirmer.utils.kotlin.nullable

inline fun <reified T> T?.toNotNull(fieldName: String, defaultValue: T? = null) : T {
    if (this == null && defaultValue == null) {
        throw Exception("Field $fieldName cannot be null.")
    }
    return this ?: defaultValue!!
}