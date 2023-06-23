package com.example.photoshell.ui.albums.photos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.photoshell.data.retrofitclasses.UnsplashPhoto
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectionPhotosViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = CollectionPhotosRepository(getApplication())
    private val handler = CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }
    private var photosMutableLiveData = MutableLiveData<List<UnsplashPhoto>>()
    val photosInCollection: MutableLiveData<List<UnsplashPhoto>>
        get() = photosMutableLiveData

    fun getPhotos(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newPhotos = repository.getPhotos(id)
            photosInCollection.postValue(newPhotos)
        }
    }

    fun openPhotoById(id: String, onGetPhoto: (unsplashPhoto: UnsplashPhoto) -> Unit) {
        viewModelScope.launch(Dispatchers.Main + handler) {
            val photo = repository.getPhoto(id)
            onGetPhoto(photo)
        }
    }

    fun addPhotos(id: String) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            val newPhotos = repository.getPhotos(id)
            val newListOfPhoto: List<UnsplashPhoto> =
                photosInCollection.value?.plus(newPhotos) ?: newPhotos
            photosMutableLiveData.postValue(newListOfPhoto)
        }
    }

    suspend fun getCollectionName(id: String): String {
        return repository.getCollectionName(id)
    }
}