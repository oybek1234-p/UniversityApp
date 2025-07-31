package com.example.universityapp.ui.universities

import com.example.universityapp.data.model.University

data class UniversityListState(
    val isLoading: Boolean = false,
    val universities: List<University> = emptyList(),
    val error: String? = null
)