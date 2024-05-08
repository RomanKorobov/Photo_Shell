package com.example.photoshell.data

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow

object KeyStoreManager {
    val tokenFlow = MutableStateFlow(false)
    private val keyStore = MyKeyStore()

    fun set(token: String?, context: Context) {
        if (token == null) return
        try {
            val prefs = context.getSharedPreferences("Shared Prefs", Context.MODE_PRIVATE)
            val encrypted = keyStore.encryptData(token)
            prefs.edit().putString("token", encrypted).apply()
        } catch (_: Exception) {
        }
    }

    fun get(context: Context): String? {
        return try {
            val prefs = context.getSharedPreferences("Shared Prefs", Context.MODE_PRIVATE)
            val data = prefs.getString("token", null) ?: return null
            val decrypted = keyStore.decryptData(data)
            tokenFlow.value = true
            decrypted
        } catch (e: Exception) {
            null
        }
    }
}