package com.example.universityapp.data.api

import com.example.universityapp.data.model.University
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search")
    suspend fun getUniversities(@Query("country") country: String): List<University>
}