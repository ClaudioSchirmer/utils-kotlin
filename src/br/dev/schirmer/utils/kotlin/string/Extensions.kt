package br.dev.schirmer.utils.kotlin.string

import java.text.Normalizer

inline fun <reified TResult : Any> String.onlyDigits(): TResult = this.filter { it.isDigit() }.let {
    return when (TResult::class) {
        String::class -> it as TResult
        Int::class -> (if(it.isNotBlank()) it.toInt() else 0) as TResult
        Long::class -> (if(it.isNotBlank()) it.toLong() else 0L) as TResult
        Double::class -> (if (it.isNotBlank()) it.toDouble() else 0.0) as TResult
        else -> throw Throwable("String.onlyDigits() only supports return types such as String, Int, Long, or Double!")
    }
}

fun String.removeDiacritics(): String =
    Regex("\\p{InCombiningDiacriticalMarks}+").replace(Normalizer.normalize(this, Normalizer.Form.NFD), "")