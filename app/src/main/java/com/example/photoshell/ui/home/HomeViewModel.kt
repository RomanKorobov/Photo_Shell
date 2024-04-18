package com.example.photoshell.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.photoshell.data.retrofitclasses.UnsplashPhoto
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(app: Application) : AndroidViewModel(app) {
    private val handler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
    }
    private val repository = HomeRepository(getApplication())
    private var photosMutableLiveData = MutableLiveData<List<UnsplashPhoto>>()
    val photos: MutableLiveData<List<UnsplashPhoto>>
        get() = photosMutableLiveData

    fun getPhotos() {
        viewModelScope.launch(Dispatchers.IO + handler) {
            val newPhotos = repository.getPhotos()
            photosMutableLiveData.postValue(newPhotos)
        }
    }

    fun addPhotos() {
        viewModelScope.launch(Dispatchers.IO + handler) {
            val newPhotos = repository.getPhotos()
            val newListOfPhoto: List<UnsplashPhoto> = photos.value?.plus(newPhotos) ?: newPhotos
            photosMutableLiveData.postValue(newListOfPhoto)
        }
    }

    fun openPhotoById(id: String, onGetPhoto: (unsplashPhoto: UnsplashPhoto) -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            val photo = repository.getPhoto(id)
            onGetPhoto(photo)
        }
    }
}