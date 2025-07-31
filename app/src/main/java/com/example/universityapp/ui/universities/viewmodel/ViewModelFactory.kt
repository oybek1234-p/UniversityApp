package com.example.universityapp.ui.universities.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.universityapp.data.repository.UniversityRepository

class ViewModelFactory(private val repository: UniversityRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UniversityViewModel::class.java)) {
            return UniversityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}