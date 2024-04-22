package com.example.photoshell.data

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyPairGenerator
import java.security.KeyStore
import javax.crypto.Cipher

object TokenStorage {
    private const val KEYSTORE_ALIAS = "Token key store"
    private val keyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore")
    private val spec = KeyGenParameterSpec.Builder(KEYSTORE_ALIAS, KeyProperties.PURPOSE_DECRYPT)
        .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
        .setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)
        .build()
    private val kpGenerator: KeyPairGenerator =
        KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore")

    fun saveToken(context: Context, token: String) {
        if (!keyStore.containsAlias(KEYSTORE_ALIAS)) {
            keyStore.load(null)
            kpGenerator.initialize(spec)
            kpGenerator.genKeyPair()
        }
        val publicKey = keyStore.getCertificate(KEYSTORE_ALIAS).publicKey
        val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        val encryptedToken = cipher.doFinal(token.toByteArray(Charsets.UTF_8))
        val encryptedStr = Base64.encodeToString(encryptedToken, Base64.DEFAULT)
        val prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        prefs.edit().putString("token", encryptedStr).apply()
    }

    fun getToken(context: Context): String? {
        return try {
            val prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val encryptedStr = prefs.getString("token", null)
            if (encryptedStr != null) {
                val privateKey = keyStore.getKey(KEYSTORE_ALIAS, null)
                val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding")
                cipher.init(Cipher.DECRYPT_MODE, privateKey)
                val decryptedToken = cipher.doFinal(Base64.decode(encryptedStr, Base64.DEFAULT))
                String(decryptedToken, Charsets.UTF_8)
            } else null
        } catch (n: NullPointerException) {
            null
        }
    }

    fun deleteToken(context: Context) {
        val prefs = context.getSharedPreferences("token", Context.MODE_PRIVATE)
        if (prefs.contains("token")) {
            prefs.edit().remove("token").apply()
        }
        if (keyStore.containsAlias(KEYSTORE_ALIAS)) {
            keyStore.deleteEntry(KEYSTORE_ALIAS)
        }
    }

}