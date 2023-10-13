package com.example.photoshell.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Network {
    private val client = OkHttpClient.Builder().build()
    private val retrofit = Retrofit.Builder().client(client).baseUrl(AuthConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

    val api: UnsplashApi
        get() = retrofit.create()
}