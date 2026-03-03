package com.example.imageapi

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MovieApi {

    private val apiKey = "fd67a0466eec661a58f6432d3118d35b"

    suspend fun getPopularMovies(): List<Movie> {

        val response: MovieResponse =
            HttpClientFactory.client.get(
                "http://api.themoviedb.org/3/movie/popular"
            ) {
                parameter("api_key", apiKey)
            }.body()

        return response.results
    }

    suspend fun searchMovies(query: String): List<Movie> {

        val response: MovieResponse =
            HttpClientFactory.client.get(
                "http://api.themoviedb.org/3/search/movie"
            ) {
                parameter("query", query)
                parameter("api_key", apiKey)
            }.body()

        return response.results
    }
}