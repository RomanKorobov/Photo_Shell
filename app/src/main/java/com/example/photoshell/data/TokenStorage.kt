package com.example.photoshell.data

import android.content.Context
import android.security.KeyPairGeneratorSpec
import android.util.Base64
import java.math.BigInteger
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.util.Calendar
import javax.crypto.Cipher
import javax.security.auth.x500.X500Principal

object TokenStorage {
    private const val KEY_ALIAS = "TokenKey"
    fun saveToken(context: Context, token: String) {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
        if (!keyStore.containsAlias(KEY_ALIAS)) {
            val start = Calendar.getInstance()
            val end = Calendar.getInstance()
            end.add(Calendar.YEAR, 25)

            val spec = KeyPairGeneratorSpec.Builder(context)
                .setAlias(KEY_ALIAS)
                .setSubject(X500Principal("CN=$KEY_ALIAS"))
                .setSerialNumber(BigInteger.ONE)
                .setStartDate(start.time)
                .setEndDate(end.time)
                .build()

            val kpGenerator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore")
            kpGenerator.initialize(spec)
            kpGenerator.generateKeyPair()
        }
        // Получение открытого ключа из хранилища
        val privateKeyEntry = keyStore.getEntry(KEY_ALIAS, null) as KeyStore.PrivateKeyEntry
        val publicKey = privateKeyEntry.certificate.publicKey

        // Шифрование токена с помощью открытого ключа
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        val encryptedToken = Base64.encodeToString(
            cipher.doFinal(token.toByteArray(Charsets.UTF_8)),
            Base64.DEFAULT
        )

        // Сохранение зашифрованного токена в SharedPreferences
        val prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        prefs.edit().putString("token", encryptedToken).apply()
    }

    fun getToken(context: Context): String? {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
        val privateKeyEntry = keyStore.getEntry(KEY_ALIAS, null) as KeyStore.PrivateKeyEntry
        val privateKey = privateKeyEntry.privateKey
        val prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val encryptedToken = prefs.getString("token", null)

        if (encryptedToken != null) {
            val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
            cipher.init(Cipher.DECRYPT_MODE, privateKey)
            val decryptedToken = cipher.doFinal(Base64.decode(encryptedToken, Base64.DEFAULT))
            return String(decryptedToken, Charsets.UTF_8)
        }
        return null
    }

    fun deleteToken(context: Context) {
        val prefs = context.getSharedPreferences("token", Context.MODE_PRIVATE)
        prefs.edit().remove("token").apply()
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
        keyStore.deleteEntry(KEY_ALIAS)
    }

}