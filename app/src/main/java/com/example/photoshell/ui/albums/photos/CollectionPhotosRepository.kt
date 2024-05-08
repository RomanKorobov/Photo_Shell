package com.example.photoshell.ui.albums.photos

import android.content.Context
import com.example.photoshell.data.Network
import com.example.photoshell.data.KeyStoreManager
import com.example.photoshell.data.retrofitclasses.UnsplashPhoto

class CollectionPhotosRepository(context: Context) {
    private val token = KeyStoreManager.get(context)
    private val accessToken = "Bearer $token"
    private val api = Network.api
    private var page = 0
    suspend fun getPhotos(id: String): List<UnsplashPhoto> {
        page++
        return api.getPhotosInCollection(accessToken, id, page)
    }

    suspend fun getPhoto(id: String): UnsplashPhoto {
        return api.getPhoto(accessToken, id)
    }

    suspend fun getCollectionName(id: String): String {
        val collection = api.getCollection(accessToken, id)
        return collection.title
    }
}