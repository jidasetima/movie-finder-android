package com.jogasoft.moviefinder.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jogasoft.moviefinder.BuildConfig
import com.jogasoft.moviefinder.R
import com.jogasoft.moviefinder.data.Movie
import com.jogasoft.moviefinder.data.MovieCategory
import com.jogasoft.moviefinder.ui.component.MovieItemImageLoader
import com.jogasoft.moviefinder.ui.component.MovieItemNoImagePlaceholder
import com.jogasoft.moviefinder.ui.theme.MovieFinderTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    moviePagerItems: LazyPagingItems<Movie>,
    searchText: String,
    updateSearchTextAction: (String) -> Unit,
    navigateToMovieDetailAction: (Int) -> Unit,
    navigateBackAction: () -> Unit
) {
    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RectangleShape,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedIndicatorColor = MaterialTheme.colorScheme.onBackground
                ),
                value = searchText,
                onValueChange = { text ->
                    updateSearchTextAction(text)
                },
                singleLine = true,
                placeholder = {
                    Text(text = stringResource(R.string.search_movies))
                },
                leadingIcon = {
                    IconButton(onClick = navigateBackAction) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = stringResource(R.string.back_navigation_arrow_button)
                        )
                    }
                },
                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        IconButton(
                            onClick = { updateSearchTextAction("") }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                tint = MaterialTheme.colorScheme.onBackground,
                                contentDescription = stringResource(R.string.clear_search_button)
                            )
                        }
                    }
                }
            )

            LazyVerticalGrid(
                contentPadding = PaddingValues(8.dp),
                columns = GridCells.Adaptive(120.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(count = moviePagerItems.itemCount) { index: Int ->
                     moviePagerItems[index]?.let { movie ->
                        SearchMovieItem(
                            movie = movie,
                            navigateToMovieDetailAction = { navigateToMovieDetailAction(movie.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchMovieItem(
    movie: Movie,
    navigateToMovieDetailAction: () -> Unit
) {
    movie.posterPath?.let {
        MovieItemImageLoader(
            imageUrl = "${BuildConfig.BASE_TMDB_IMAGE_URL}/w342/${movie.posterPath}",
            movieTitle = movie.title,
            navigateToMovieDetailAction = navigateToMovieDetailAction
        )
    } ?: run {
        MovieItemNoImagePlaceholder(
            modifier = Modifier
                .aspectRatio(2f / 3f),
            title = movie.title
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewSearchScreen() {
    val movies = List(20) { index ->
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
    MovieFinderTheme {
        SearchScreen(
            moviePagerItems = flowOf(PagingData.from(movies)).collectAsLazyPagingItems(),
            searchText = "",
            updateSearchTextAction = {},
            navigateToMovieDetailAction = {},
            navigateBackAction = {}
        )
    }
}
