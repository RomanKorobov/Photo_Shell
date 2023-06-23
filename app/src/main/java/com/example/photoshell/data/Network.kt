package com.example.photoshell.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Network {
    private var client: OkHttpClient? = null
    private var retrofit: Retrofit? = null
    fun getStarted() {
        client = OkHttpClient.Builder().build()
        retrofit = Retrofit.Builder().client(client!!).baseUrl(AuthConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val api: UnsplashApi
        get() = retrofit?.create()!!
}