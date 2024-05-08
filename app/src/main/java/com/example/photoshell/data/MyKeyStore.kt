package com.example.photoshell.data

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.Cipher

class MyKeyStore {
    private val transformation = "RSA/ECB/PKCS1Padding"
    private var keyPair: KeyPair? = null

    private fun getPrivateKey(): PrivateKey? {
        if (keyPair == null) {
            generateKey()
        }
        return keyPair!!.private
    }

    private fun getPublicKey(): PublicKey? {
        if (keyPair == null) {
            generateKey()
        }
        return keyPair!!.public
    }

    fun encryptData(data: String): String {
        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey())
        val encryptedBytes = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

    fun decryptData(encryptedData: String): String {
        val encryptedBytes = Base64.decode(encryptedData, Base64.DEFAULT)
        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey())
        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes)
    }

    private fun generateKey() {
        val keyGenerator =
            KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore")
        val algorithm = KeyGenParameterSpec.Builder(
            "KEYSTORE_ALIAS",
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
            .build()
        keyGenerator.initialize(algorithm)
        keyPair = keyGenerator.generateKeyPair()
    }
}