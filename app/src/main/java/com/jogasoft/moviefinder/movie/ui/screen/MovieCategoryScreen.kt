package com.jogasoft.moviefinder.movie.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.jogasoft.moviefinder.BuildConfig
import com.jogasoft.moviefinder.R
import com.jogasoft.moviefinder.core.ui.component.MovieFinderAppBar
import com.jogasoft.moviefinder.core.ui.theme.MovieFinderTheme
import com.jogasoft.moviefinder.data.Movie
import com.jogasoft.moviefinder.data.MovieCategory
import com.jogasoft.moviefinder.movie.ui.component.MovieItemImageLoader
import com.jogasoft.moviefinder.movie.ui.component.MovieItemNoImagePlaceholder
import com.jogasoft.moviefinder.movie.ui.viewModel.MovieCategoryListUiState

// Test tags
const val MovieCategoryListLazyColumnTestTag = "MovieCategoryListLazyColumnTestTag"

@Composable
fun MovieCategoryListScreen(
    modifier: Modifier = Modifier,
    uiState: MovieCategoryListUiState,
    navigateToMovieDetail: (Int) -> Unit,
    navigateToSettings: () -> Unit,
    navigateToSearch: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MovieFinderAppBar(
                actions = {
                    IconButton(onClick = navigateToSearch) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.search_icon_button)
                        )
                    }
                    IconButton(onClick = navigateToSettings) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.settings_icon_button)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .testTag(MovieCategoryListLazyColumnTestTag),
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
                movie.posterPath?.let {
                    MovieItemImageLoader(
                        imageUrl = "${BuildConfig.BASE_TMDB_IMAGE_URL}/w342/${movie.posterPath}",
                        movieTitle = movie.title,
                        navigateToMovieDetailAction = { navigateToMovieDetail(movie.id) }
                    )
                } ?: run {
                    MovieItemNoImagePlaceholder(
                        modifier = Modifier
                            .aspectRatio(2f / 3f),
                        title = movie.title
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewMovieCategoryListScreen() {
    MovieFinderTheme {
        MovieCategoryListScreen(
            uiState = MovieCategoryListUiState(
                nowPlayingMovies = generatePreviewMovieListByCategory(MovieCategory.NOW_PLAYING),
                popularMovies = generatePreviewMovieListByCategory(MovieCategory.POPULAR),
                topRatedMovies = generatePreviewMovieListByCategory(MovieCategory.TOP_RATED),
                upcomingMovies = generatePreviewMovieListByCategory(MovieCategory.UPCOMING)
            ),
            navigateToMovieDetail = {},
            navigateToSettings = {},
            navigateToSearch = {}
        )
    }
}

private fun generatePreviewMovieListByCategory(category: MovieCategory) = List(20) { index ->
    Movie(
        id = index,
        backdropPath = "/backdrop1.jpg",
        overview = "A thrilling adventure of a group of friends who find themselves trapped in a mysterious cave.",
        posterPath = "/poster1.jpg",
        releaseDate = "2023-07-15",
        title = "The Hidden Depths",
        category = category
    )
}