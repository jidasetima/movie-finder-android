package com.jogasoft.moviefinder.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.jogasoft.moviefinder.movie.ui.screen.MovieCategoryListScreen
import com.jogasoft.moviefinder.movie.ui.screen.MovieDetailScreen
import com.jogasoft.moviefinder.movie.ui.screen.SearchScreen
import com.jogasoft.moviefinder.movie.ui.viewModel.MovieCategoryListViewModel
import com.jogasoft.moviefinder.movie.ui.viewModel.MovieDetailViewModel

import com.jogasoft.moviefinder.movie.ui.viewModel.SearchViewModel
import com.jogasoft.moviefinder.userpreferences.ui.SettingsViewModel
import com.jogasoft.moviefinder.userpreferences.ui.screen.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
object MovieCategoryListScreen

@Serializable
data class MovieDetailScreen(val movieId: Int)

@Serializable
object SearchScreen

@Serializable
object SettingsScreen

// Test Tags
const val NavHostTestTag = "NavHostTestTag"
const val NavHostMovieCategoryListScreenTestTag = "NavHostMovieCategoryListScreenTestTag"
const val NavHostMovieDetailScreenTestTag = "NavHostMovieDetailScreenTestTag"
const val NavHostSearchScreenScreenTestTag = "NavHostSearchScreenScreenTestTag"
const val NavHostSettingsScreenTestTag = "NavHostSettingsScreenTestTag"

@Composable
fun MovieFinderAppNavigationHost(navController: NavHostController) {
    NavHost(
        modifier = Modifier.testTag(NavHostTestTag),
        navController = navController,
        startDestination = MovieCategoryListScreen,
    ) {
        composable<MovieCategoryListScreen> {
            val viewModel = hiltViewModel<MovieCategoryListViewModel>()
            MovieCategoryListScreen(
                modifier = Modifier.testTag(NavHostMovieCategoryListScreenTestTag),
                uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                navigateToMovieDetail = { movieId ->
                    navController.navigate(MovieDetailScreen(movieId))
                },
                navigateToSettings = { navController.navigate(SettingsScreen) },
                navigateToSearch = { navController.navigate(SearchScreen) }
            )
        }

        composable<MovieDetailScreen> {
            val viewModel = hiltViewModel<MovieDetailViewModel>()
            MovieDetailScreen(
                modifier = Modifier.testTag(NavHostMovieDetailScreenTestTag),
                uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                navigateBackAction = { navController.popBackStack() }
            )
        }

        composable<SearchScreen> {
            val viewModel = hiltViewModel<SearchViewModel>()
            SearchScreen(
                modifier = Modifier.testTag(NavHostSearchScreenScreenTestTag),
                moviePagerItems = viewModel.moviePagerState.collectAsLazyPagingItems(),
                searchText = viewModel.searchTextState.collectAsStateWithLifecycle().value,
                updateSearchTextAction = viewModel::updateSearchText,
                navigateBackAction = { navController.popBackStack() },
                navigateToMovieDetailAction = { movieId ->
                    navController.navigate(MovieDetailScreen(movieId))
                }
            )
        }

        composable<SettingsScreen> {
            val viewModel = hiltViewModel<SettingsViewModel>()
            SettingsScreen(
                modifier = Modifier.testTag(NavHostSettingsScreenTestTag),
                uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                navigateBackAction = { navController.popBackStack() },
                updateThemeAction = viewModel::updateTheme
            )
        }
    }
}