package com.example.universityapp.ui.universities.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.universityapp.data.repository.UniversityRepository
import com.example.universityapp.ui.universities.UniversityListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UniversityViewModel(
    private val repository: UniversityRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UniversityListState())
    val uiState: StateFlow<UniversityListState> = _uiState.asStateFlow()

    init {
        loadUniversities()
    }

    private fun loadUniversities() {
        viewModelScope.launch {
            _uiState.value = UniversityListState(isLoading = true)
            try {
                val universities = repository.getUniversities()
                _uiState.value = UniversityListState(universities = universities)
            } catch (e: Exception) {
                _uiState.value = UniversityListState(error = "${e.message}")
            }
        }
    }
}