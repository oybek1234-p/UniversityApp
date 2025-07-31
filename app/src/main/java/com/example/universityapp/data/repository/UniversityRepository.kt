package com.example.universityapp.data.repository

import com.example.universityapp.data.api.ApiService
import com.example.universityapp.data.model.University

class UniversityRepository(private val apiService: ApiService) {
    suspend fun getUniversities(): List<University> {
        return apiService.getUniversities("United States")
    }
}