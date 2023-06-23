package com.example.photoshell.ui.albums

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.photoshell.data.retrofitclasses.Collection
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumsViewModel(app: Application): AndroidViewModel(app) {
    private val repository = AlbumsRepository(getApplication())
    private val handler = CoroutineExceptionHandler{_, throwable -> throwable.printStackTrace()}
    private var collectionsMutableLiveData = MutableLiveData<List<Collection>>()
    val collections: MutableLiveData<List<Collection>>
        get() = collectionsMutableLiveData
    fun getCollections() {
        viewModelScope.launch (Dispatchers.IO + handler){
            val newCollections = repository.getCollections()
            collectionsMutableLiveData.postValue(newCollections)
        }
    }
}