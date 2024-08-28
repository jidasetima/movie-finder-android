package com.jogasoft.moviefinder.ui.screen

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.jogasoft.moviefinder.BuildConfig
import com.jogasoft.moviefinder.R
import com.jogasoft.moviefinder.data.Movie
import com.jogasoft.moviefinder.data.MovieCategory
import com.jogasoft.moviefinder.ui.HomeUiState
import com.jogasoft.moviefinder.ui.component.MovieFinderAppBar
import com.jogasoft.moviefinder.ui.theme.MovieFinderTheme

// Test tags
const val HomeLazyColumnTestTag = "HomeLazyColumnTestTag"

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    navigateToMovieDetail: (Int) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = { MovieFinderAppBar() }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .testTag(HomeLazyColumnTestTag),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                LazyMovieRow(
                    sectionTitle = stringResource(R.string.now_playing),
                    movies = uiState.nowPlayingMovies,
                    navigateToMovieDetail = navigateToMovieDetail
                )
            }

            item {
                LazyMovieRow(
                    sectionTitle = stringResource(R.string.popular),
                    movies = uiState.popularMovies,
                    navigateToMovieDetail = navigateToMovieDetail
                )
            }

            item {
                LazyMovieRow(
                    sectionTitle = stringResource(R.string.top_rated),
                    movies = uiState.topRatedMovies,
                    navigateToMovieDetail = navigateToMovieDetail
                )
            }

            item {
                LazyMovieRow(
                    sectionTitle = stringResource(R.string.upcoming),
                    movies = uiState.upcomingMovies,
                    navigateToMovieDetail = navigateToMovieDetail
                )
            }
        }
    }
}

@Composable
private fun LazyMovieRow(
    modifier: Modifier = Modifier,
    sectionTitle: String,
    movies: List<Movie>,
    navigateToMovieDetail: (Int) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = sectionTitle,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 8.dp)
        ) {
            items(
                items = movies,
                key = { movie -> movie.id }
            ) { movie ->
                SubcomposeAsyncImage(
                    model = "${BuildConfig.BASE_TMDB_IMAGE_URL}/w342/${movie.posterPath}",
                    loading = {
                        AnimatedPlaceholderMovie()
                    },
                    error = {
                        AnimatedPlaceholderMovie()
                    },
                    contentDescription = "${movie.title} image",
                    modifier = Modifier
                        .aspectRatio(2f / 3f)
                        .clickable { navigateToMovieDetail(movie.id) }
                )
            }
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
    Box(
        modifier = Modifier
            .background(color)
    )
}

@PreviewLightDark
@Composable
fun PreviewHomeScreen() {
    
    val nowPlayingMovies = List(20) { index ->
        Movie(
            id = index,
            backdropPath = "/backdrop1.jpg",
            overview = "A thrilling adventure of a group of friends who find themselves trapped in a mysterious cave.",
            posterPath = "/poster1.jpg",
            releaseDate = "2023-07-15",
            title = "The Hidden Depths",
            category = MovieCategory.NOW_PLAYING
        )
    }

    val popularMovies = List(20) { index ->
        Movie(
            id = index,
            backdropPath = "/backdrop1.jpg",
            overview = "A thrilling adventure of a group of friends who find themselves trapped in a mysterious cave.",
            posterPath = "/poster1.jpg",
            releaseDate = "2023-07-15",
            title = "The Hidden Depths",
            category = MovieCategory.POPULAR
        )
    }

    val topRatedMovies = List(20) { index ->
        Movie(
            id = index,
            backdropPath = "/backdrop1.jpg",
            overview = "A thrilling adventure of a group of friends who find themselves trapped in a mysterious cave.",
            posterPath = "/poster1.jpg",
            releaseDate = "2023-07-15",
            title = "The Hidden Depths",
            category = MovieCategory.TOP_RATED
        )
    }

    val upcomingMovies = List(20) { index ->
        Movie(
            id = index,
            backdropPath = "/backdrop1.jpg",
            overview = "A thrilling adventure of a group of friends who find themselves trapped in a mysterious cave.",
            posterPath = "/poster1.jpg",
            releaseDate = "2023-07-15",
            title = "The Hidden Depths",
            category = MovieCategory.UPCOMING
        )
    }

    MovieFinderTheme {
        HomeScreen(
            uiState = HomeUiState(
                nowPlayingMovies = nowPlayingMovies,
                popularMovies = popularMovies,
                topRatedMovies = topRatedMovies,
                upcomingMovies = upcomingMovies
            ),
            navigateToMovieDetail = {}
        )
    }
}