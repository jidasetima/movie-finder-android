package com.jogasoft.moviefinder.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import coil.compose.SubcomposeAsyncImage
import com.jogasoft.moviefinder.BuildConfig
import com.jogasoft.moviefinder.data.MovieDetail
import com.jogasoft.moviefinder.ui.MovieDetailUiState
import com.jogasoft.moviefinder.ui.component.MovieFinderAppBar
import com.jogasoft.moviefinder.ui.theme.MovieFinderTheme

@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    uiState: MovieDetailUiState,
    navigateBackAction: () -> Unit
) {
    Scaffold(
        topBar = {
            MovieFinderAppBar(navigateBackAction = navigateBackAction)
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {
            when (uiState) {
                MovieDetailUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is MovieDetailUiState.Success -> {
                    SubcomposeAsyncImage(
                        model = "${BuildConfig.BASE_TMDB_IMAGE_URL}/w780/${uiState.movieDetail.backdropPath}",
                        contentDescription = "Movie Backdrop Image",
                        modifier = modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                    )

                    Text(
                        text = uiState.movieDetail.title,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = uiState.movieDetail.overview,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewMovieDetailScreen() {
    MovieFinderTheme {
        MovieDetailScreen(
            uiState = MovieDetailUiState.Success(
                movieDetail = MovieDetail(
                    id = 1,
                    backdropPath = "Fake Path",
                    budget = 1,
                    overview = "Fake Overview",
                    popularity = 1.0,
                    posterPath = "Fake Path",
                    releaseDate = "Fake Date",
                    runtime = 1,
                    tagline = "Fake Tagline",
                    title = "Fake Title"
                )
            ),
            navigateBackAction = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewMovieDetailScreenLoading() {
    MovieFinderTheme {
        MovieDetailScreen(
            uiState = MovieDetailUiState.Loading,
            navigateBackAction = {}
        )
    }
}