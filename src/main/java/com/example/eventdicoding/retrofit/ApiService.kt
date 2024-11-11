package com.example.eventdicoding.retrofit

import com.example.eventdicoding.data.DetailEvent
import com.example.eventdicoding.data.ListEventResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    suspend fun getEventsByStatus(
        @Query("active") status: Int
    ): ListEventResponse
    @GET("events/{id}")
    suspend fun getDetailEvent(@Path("id") id: String): DetailEvent

}

