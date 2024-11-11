package com.example.eventdicoding.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.eventdicoding.ui.database.AppDatabase
import com.example.eventdicoding.ui.database.FavoriteEvent
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val favoriteEventDao = AppDatabase.getDatabase(application).favoriteEventDao()

    private val _favoriteEvents = MutableLiveData<List<FavoriteEvent>>()
    val favoriteEvents: LiveData<List<FavoriteEvent>> get() = _favoriteEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        loadFavoriteEvents()
    }

    fun loadFavoriteEvents() {
        _isLoading.value = true
        viewModelScope.launch {
            val events = favoriteEventDao.getAllFavoriteEvent()
            _favoriteEvents.value = events.value ?: emptyList()
            _isLoading.value = false
        }
    }

    fun loadFavorite(): LiveData<List<FavoriteEvent>> = liveData {
        val favoriteEvents = favoriteEventDao.getAllFavoriteEvent()
        emitSource(favoriteEvents)
    }

}