package br.dev.schirmer.utils.kotlin.security

import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class Cryptography(private val key: String) {

    companion object {
        fun getNewKey(): String = KeyGenerator.getInstance("AES").run {
            init(256)
            Base64.getEncoder().encodeToString(generateKey().encoded)
        }
    }

    fun encryptURL(url: String): String = Base64.getUrlEncoder().encode(encrypt(url.toByteArray())).decodeToString()

    fun decryptURL(url: String): String = decrypt(Base64.getUrlDecoder().decode(url)).decodeToString()

    fun encrypt(text: String): String = encrypt(text.toByteArray()).decodeToString()

    fun decrypt(text: String): String = decrypt(text.toByteArray()).decodeToString()

    fun encrypt(bytes: ByteArray): ByteArray = try {
        val secretKey = SecretKeySpec(Base64.getDecoder().decode(key), "AES")
        val ivByteArray = SecureRandom().generateSeed(16)
        val iv = IvParameterSpec(ivByteArray)
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv)
        val encrypted = cipher.doFinal(bytes)
        Base64.getEncoder().encode(ivByteArray + encrypted)
    } catch (e: Exception) {
        byteArrayOf()
    }

    fun decrypt(bytes: ByteArray): ByteArray = try {
        val textByteArray = Base64.getDecoder().decode(bytes)
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val secretKey = SecretKeySpec(Base64.getDecoder().decode(key), "AES")
        val iv = IvParameterSpec(textByteArray.take(16).toByteArray())
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv)
        cipher.doFinal(textByteArray.drop(16).toByteArray())
    } catch (e: Exception) {
        byteArrayOf()
    }
}