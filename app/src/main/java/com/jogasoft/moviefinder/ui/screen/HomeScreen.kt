package com.jogasoft.moviefinder.ui.screen

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.jogasoft.moviefinder.BuildConfig
import com.jogasoft.moviefinder.data.Movie
import com.jogasoft.moviefinder.ui.HomeUiState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 100.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = uiState.nowPlayingMovies,
            key = { movie -> movie.id }
        ) { movie ->
            SubcomposeAsyncImage(
                model = "${BuildConfig.BASE_TMDB_IMAGE_URL}/w400/${movie.posterPath}",
                loading = {
                    AnimatedPlaceholderMovie()
                },
                error = {
                    AnimatedPlaceholderMovie()
                },
                contentDescription = "${movie.title} image",
                modifier = Modifier.aspectRatio(2f / 3f)
            )
        }
    }
}

@Composable
private fun AnimatedPlaceholderMovie() {
    val infiniteTransition = rememberInfiniteTransition(label = "AnimatedPlaceholderMovie")
    val color by infiniteTransition.animateColor(
        initialValue = Color.DarkGray,
        targetValue = Color.Gray,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ), label = "AnimatedPlaceholderMovie Color"
    )
    Box(modifier = Modifier
        .background(color)
    )
}


@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(
        uiState = HomeUiState(
            nowPlayingMovies = List(20) { index: Int ->
                Movie(
                    id = index,
                    backdropPath = "/backdrop1.jpg",
                    overview = "A thrilling adventure of a group of friends who find themselves trapped in a mysterious cave.",
                    posterPath = "/poster1.jpg",
                    releaseDate = "2023-07-15",
                    title = "The Hidden Depths"
                )
            }
        )
    )
}