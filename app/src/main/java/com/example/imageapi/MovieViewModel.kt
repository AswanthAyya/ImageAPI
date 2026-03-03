package com.example.imageapi

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {

    private val api = MovieApi()

    var allMovies by mutableStateOf<List<Movie>>(emptyList())
        private set

    var filteredMovies by mutableStateOf<List<Movie>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var searchText by mutableStateOf("")
        private set

    fun loadMovies() {
        viewModelScope.launch {
            isLoading = true
            try {
                //val result = api.searchMovies("batman")
                val result = api.getPopularMovies()
                allMovies = result
                filteredMovies = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
            isLoading = false
        }
    }

    fun onSearchChange(query: String) {
        searchText = query

        viewModelScope.launch {
            if (query.isBlank()) {
                filteredMovies = allMovies
            } else {
                isLoading = true
                try {
                    val result = api.searchMovies(query)
                    filteredMovies = result
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                isLoading = false
            }
        }
    }
}