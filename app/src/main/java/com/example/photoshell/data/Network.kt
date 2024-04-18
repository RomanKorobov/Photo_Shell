package com.example.photoshell.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Network {
    val client = OkHttpClient.Builder().build()
    val gson: Gson = GsonBuilder().setLenient().create()
    private val retrofit = Retrofit.Builder().client(client).baseUrl(AuthConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()

    val api: UnsplashApi
        get() = retrofit.create()

}