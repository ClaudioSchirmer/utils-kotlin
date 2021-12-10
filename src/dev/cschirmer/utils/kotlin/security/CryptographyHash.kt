package dev.cschirmer.utils.kotlin.security

import java.security.MessageDigest
import java.security.SecureRandom
import java.util.*

object CryptographyHash {

    private fun encryptWithSalt(value: String, salt: ByteArray) = try {
        val digest = MessageDigest.getInstance("SHA-512")
        digest.reset()
        digest.update(salt)
        digest.digest(value.toByteArray(charset("utf8")))?.let {
            Base64.getEncoder().encodeToString(
                salt.take(8).toByteArray() + it + salt.takeLast(8).toByteArray()
            )
        } ?: ""
    } catch (e: Exception) {
        ""
    }

    fun encrypt(value: String) = encryptWithSalt(
        value = value,
        salt = SecureRandom().run {
            val byteArray = ByteArray(16)
            nextBytes(byteArray)
            byteArray
        }
    )

    fun equalsHash(value: String, hash: String): Boolean = encryptWithSalt(
        value = value,
        salt = Base64.getDecoder().decode(hash).run {
            take(8).toByteArray() + takeLast(8).toByteArray()
        }
    ) == hash
}