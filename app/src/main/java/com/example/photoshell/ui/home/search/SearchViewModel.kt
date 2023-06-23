package com.example.photoshell.ui.home.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.photoshell.data.retrofitclasses.UnsplashPhoto
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = SearchRepository(getApplication())
    private val handler = CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }
    private var mutableSearchPhotosLiveData = MutableLiveData<List<UnsplashPhoto>>()
    val searchPhotos: MutableLiveData<List<UnsplashPhoto>>
        get() = mutableSearchPhotosLiveData

    fun searchPhotos(query: String) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            repository.queryString = query
            val resultPhotos = repository.searchPhotos()
            mutableSearchPhotosLiveData.postValue(resultPhotos)
        }
    }

    suspend fun getPhoto(id: String): UnsplashPhoto {
        return repository.getPhoto(id)
    }

    fun addPhotos() {
        viewModelScope.launch(Dispatchers.IO + handler) {
            val newPhotos = repository.searchPhotos()
            val newListOfPhoto: List<UnsplashPhoto> =
                searchPhotos.value?.plus(newPhotos) ?: newPhotos
            mutableSearchPhotosLiveData.postValue(newListOfPhoto)
        }
    }
}