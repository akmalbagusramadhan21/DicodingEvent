package com.example.eventdicoding.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventdicoding.data.ListEventsItem
import com.example.eventdicoding.retrofit.ApiConfig
import kotlinx.coroutines.launch

class FinishedViewModel : ViewModel() {

    private val _event = MutableLiveData<List<ListEventsItem>>()
    val event: LiveData<List<ListEventsItem>> get() = _event

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchApi() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val client = ApiConfig.getApiService()
                val response = client.getEventsByStatus(0)

                if (response.error == false) {
                    val listEvents = response.listEvents
                    _event.value = listEvents
                } else {
                    _event.value = emptyList()
                }
            } catch (e: Exception) {
                _event.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
