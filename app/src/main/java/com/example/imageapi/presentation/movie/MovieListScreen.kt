package com.example.imageapi.presentation.movie

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.imageapi.domain.model.Movie
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MovieListScreen(navController: NavController) {

    val viewModel: MovieViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.loadMovies()
    }

    Column(modifier = Modifier.fillMaxSize()) {

        OutlinedTextField(
            value = viewModel.searchText,
            onValueChange = { viewModel.onSearchChange(it) },
            label = { Text("Search movies...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        if (viewModel.isLoading) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        } else {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(
                    items = viewModel.filteredMovies,
                    key = { it.id }
                ) { movie ->

                    MovieCard(movie) {

                        // 🔥 SAFE ENCODING
                        val encodedTitle =
                            URLEncoder.encode(movie.title, StandardCharsets.UTF_8.toString())

                        val encodedOverview =
                            URLEncoder.encode(movie.overview, StandardCharsets.UTF_8.toString())

                        val encodedPoster =
                            URLEncoder.encode(movie.poster_path ?: "", StandardCharsets.UTF_8.toString())

                        navController.navigate(
                            "details/$encodedTitle/$encodedOverview/$encodedPoster"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieCard(
    movie: Movie,
    onClick: () -> Unit
) {

    val imageUrl = movie.poster_path?.let {
        "http://image.tmdb.org/t/p/w500$it"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        onClick = onClick
    ) {

        AsyncImage(
            model = imageUrl,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}