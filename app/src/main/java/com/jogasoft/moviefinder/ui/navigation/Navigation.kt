package com.jogasoft.moviefinder.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jogasoft.moviefinder.ui.HomeViewModel
import com.jogasoft.moviefinder.ui.MovieDetailViewModel
import com.jogasoft.moviefinder.ui.screen.HomeScreen
import com.jogasoft.moviefinder.ui.screen.MovieDetailScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Serializable
data class MovieDetailScreen(val movieId: Int)

// Test Tags
const val NavHostTestTag = "NavHostTestTag"
const val NavHostHomeScreenTestTag = "NavHostHomeScreenTestTag"
const val NavHostMovieDetailScreenTestTag = "NavHostHomeScreenTestTag"

@Composable
fun MovieFinderAppNavigationHost(navController: NavHostController) {
    NavHost(
        modifier = Modifier.testTag(NavHostTestTag),
        navController = navController,
        startDestination = HomeScreen,
    ) {
        composable<HomeScreen> {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                modifier = Modifier.testTag(NavHostHomeScreenTestTag),
                uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                navigateToMovieDetail = { movieId ->
                    navController.navigate(MovieDetailScreen(movieId))
                }
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
    }
}