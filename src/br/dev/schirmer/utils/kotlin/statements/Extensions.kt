package br.dev.schirmer.utils.kotlin.statements

inline fun guard(condition: Boolean, onFailure: () -> Nothing) {
    if (!condition) onFailure()
}
