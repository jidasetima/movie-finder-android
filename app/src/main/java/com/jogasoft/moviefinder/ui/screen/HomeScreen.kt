package com.jogasoft.moviefinder.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jogasoft.moviefinder.data.Movie
import com.jogasoft.moviefinder.ui.HomeUiState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        items(
            items = uiState.nowPlayingMovies,
            key = { item: Movie ->  item.id}
            )
        { movie ->
            Text(text = movie.title)
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(
        uiState = HomeUiState()
    )
}