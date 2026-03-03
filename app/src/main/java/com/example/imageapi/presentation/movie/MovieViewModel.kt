package com.example.imageapi.presentation.movie

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imageapi.data.remote.MovieApi
import com.example.imageapi.domain.model.Movie
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