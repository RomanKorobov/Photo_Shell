package com.example.photoshell.ui.albums

import android.content.Context
import com.example.photoshell.data.retrofitclasses.Collection
import com.example.photoshell.data.Network
import com.example.photoshell.data.TokenStorage

class AlbumsRepository(context: Context) {
    private val token = TokenStorage.getToken(context)
    private val accessToken = "Bearer $token"
    private val api = Network.api
    private var page = 0
    suspend fun getCollections(): List<Collection> {
        page++
        return api.getCollections(accessToken, page)
    }
}