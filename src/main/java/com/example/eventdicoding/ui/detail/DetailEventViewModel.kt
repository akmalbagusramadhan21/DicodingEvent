package com.example.eventdicoding.ui.detail


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.eventdicoding.data.Event
import com.example.eventdicoding.retrofit.ApiConfig
import com.example.eventdicoding.ui.database.AppDatabase
import com.example.eventdicoding.ui.database.FavoriteEvent
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


class DetailEventViewModel (application: Application) : AndroidViewModel(application) {

    private val _eventDetail = MutableLiveData<Event?>()
    val eventDetail: LiveData<Event?> get() = _eventDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    private val apiService = ApiConfig.getApiService()
    private val favoriteEventDao = AppDatabase.getDatabase(application).favoriteEventDao()


    fun fetchEventDetail(eventId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = apiService.getDetailEvent(eventId.toString())
                if (response.error==false) {
                    _eventDetail.value = response.event
                    checkIfFavorite(eventId)
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message.toString()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun checkIfFavorite(eventId: Int) {
        viewModelScope.launch {
            val favoriteEvent = favoriteEventDao.getFavoriteEventById(eventId).firstOrNull()
            _isFavorite.value = favoriteEvent != null
        }
    }


    fun toggleFavorite(event: Event) {
        viewModelScope.launch {
            val favoriteEvent = FavoriteEvent(
                id = event.id!!,
                name = event.name ?: "",
                mediaCover = event.mediaCover
            )
            if (_isFavorite.value == true) {
                removeFavorite(favoriteEvent)
                _isFavorite.value = false
            } else {
                addFavorite(favoriteEvent)
                _isFavorite.value = true
            }
        }
    }


    private suspend fun addFavorite(event: FavoriteEvent) {
        favoriteEventDao.insert(event)
    }


    private suspend fun removeFavorite(event: FavoriteEvent) {
        favoriteEventDao.delete(event)
    }

}