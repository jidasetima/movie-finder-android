package com.jogasoft.moviefinder.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.jogasoft.moviefinder.BuildConfig
import com.jogasoft.moviefinder.data.MovieDetail
import com.jogasoft.moviefinder.ui.MovieDetailUiState
import com.jogasoft.moviefinder.ui.component.MovieFinderAppBar
import com.jogasoft.moviefinder.ui.theme.MovieFinderTheme

//Test tags
const val MovieDetailLoadingTestTag = "MovieDetailLoadingTestTag"

@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    uiState: MovieDetailUiState,
    navigateBackAction: () -> Unit
) {
    Scaffold(topBar = {
        MovieFinderAppBar(navigateBackAction = navigateBackAction)
    }) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            when (uiState) {
                MovieDetailUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag(MovieDetailLoadingTestTag),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is MovieDetailUiState.Success -> {
                    SubcomposeAsyncImage(
                        model = "${BuildConfig.BASE_TMDB_IMAGE_URL}/w780/${uiState.movieDetail.backdropPath}",
                        contentDescription = "Movie Backdrop Image",
                        modifier = modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                    )

                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = uiState.movieDetail.title,
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = uiState.movieDetail.releaseDate,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.labelLarge
                            )

                            Text(
                                text = "${uiState.movieDetail.runtime / 60}h ${uiState.movieDetail.runtime % 60}m",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            uiState.movieDetail.genres.forEachIndexed { index, genre ->
                                Text(
                                    text = genre,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    style = MaterialTheme.typography.labelLarge
                                )

                                if (index != uiState.movieDetail.genres.lastIndex) {
                                    Text(
                                        text = "|",
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                            }
                        }

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
}

@PreviewLightDark
@Composable
private fun PreviewMovieDetailScreenSuccess() {
    MovieFinderTheme {
        MovieDetailScreen(uiState = MovieDetailUiState.Success(
            movieDetail = MovieDetail(
                id = 1,
                backdropPath = "Fake Path",
                budget = 1,
                genres = listOf("Action", "Comedy", "Romance"),
                overview = "Fake Overview",
                popularity = 1.0,
                posterPath = "Fake Path",
                releaseDate = "2012-02-10",
                runtime = 100,
                tagline = "Fake Tagline",
                title = "Fake Title"
            )
        ), navigateBackAction = {})
    }
}

@PreviewLightDark
@Composable
private fun PreviewMovieDetailScreenLoading() {
    MovieFinderTheme {
        MovieDetailScreen(uiState = MovieDetailUiState.Loading, navigateBackAction = {})
    }
}