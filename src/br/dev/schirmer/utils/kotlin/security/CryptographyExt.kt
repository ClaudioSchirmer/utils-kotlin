package br.dev.schirmer.utils.kotlin.security


fun String.encrypt(key: String): String = Cryptography(key).encrypt(this)

fun String.decrypt(key: String): String = Cryptography(key).decrypt(this)

fun String.encryptURL(key: String): String = Cryptography(key).encryptURL(this)

fun String.decryptURL(key: String): String = Cryptography(key).decryptURL(this)

fun String.encryptHash(): String = CryptographyHash.encrypt(this)

fun String.equalsHash(hash: String): Boolean = CryptographyHash.equalsHash(this, hash)