package com.example.imageapi

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun DetailScreen(
    title: String,
    overview: String,
    posterPath: String
) {

    val decodedTitle =
        URLDecoder.decode(title, StandardCharsets.UTF_8.toString())

    val decodedOverview =
        URLDecoder.decode(overview, StandardCharsets.UTF_8.toString())

    val decodedPoster =
        URLDecoder.decode(posterPath, StandardCharsets.UTF_8.toString())

    val imageUrl =
        "http://image.tmdb.org/t/p/w500$decodedPoster"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        AsyncImage(
            model = imageUrl,
            contentDescription = decodedTitle,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = decodedTitle,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = decodedOverview,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}