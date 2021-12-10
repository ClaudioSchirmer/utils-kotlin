package dev.cschirmer.utils.kotlin.extensions

import java.text.Normalizer

fun String.onlyDigits() = this.filter { it.isDigit() }

fun String.removeDiacritics(): String = Regex("\\p{InCombiningDiacriticalMarks}+").replace(Normalizer.normalize(this, Normalizer.Form.NFD), "")