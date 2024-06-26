package com.example.photoshell.ui.start

import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoshell.MainActivity
import com.example.photoshell.R
import com.example.photoshell.data.AuthConfig
import com.example.photoshell.data.Network
import com.example.photoshell.data.KeyStoreManager
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class StartViewModel(app: Application) : AndroidViewModel(app) {
    private val serviceConfig = AuthorizationServiceConfiguration(
        Uri.parse(AuthConfig.AUTHORIZE_URI),
        Uri.parse(AuthConfig.TOKEN_URI)
    )
    private val authService = AuthorizationService(getApplication())
    private val authRequest = AuthorizationRequest.Builder(
        serviceConfig,
        AuthConfig.ACCESS_KEY,
        ResponseTypeValues.CODE,
        Uri.parse(AuthConfig.REDIRECT_URI)
    ).setScope(AuthConfig.SCOPE).build()

    fun performAuthorizationRequest() {
        viewModelScope.launch {
            val pendingIntent = PendingIntent.getActivity(
                getApplication(),
                0,
                Intent(getApplication(), MainActivity::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )
            authService.performAuthorizationRequest(authRequest, pendingIntent)
        }
    }

    fun performTokenRequest(code: String) {
        viewModelScope.launch {
            val requestBody = FormBody.Builder()
                .add("client_id", AuthConfig.ACCESS_KEY)
                .add("client_secret", AuthConfig.CLIENT_SECRET)
                .add("redirect_uri", AuthConfig.REDIRECT_URI)
                .add("code", code)
                .add("grant_type", AuthConfig.GRANT_TYPE)
                .build()
            val request = Request.Builder()
                .url("https://unsplash.com/oauth/token")
                .post(requestBody)
                .build()
            Network.client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Toast.makeText(getApplication(), R.string.auth_fail, Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(call: Call, response: Response) {
                    val jsonResponse = response.body?.string() ?: ""
                    try {
                        val jsonObject =
                            Network.gson.fromJson(jsonResponse, JsonObject::class.java)
                        val accessToken = jsonObject.getAsJsonPrimitive("access_token").asString
                        KeyStoreManager.set(accessToken, getApplication())
                    } catch (_: RuntimeException) {}
                }
            })
        }
    }
}