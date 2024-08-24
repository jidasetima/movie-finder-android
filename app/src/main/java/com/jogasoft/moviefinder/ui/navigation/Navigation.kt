package com.jogasoft.moviefinder.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jogasoft.moviefinder.ui.HomeViewModel
import com.jogasoft.moviefinder.ui.MovieDetailViewModel
import com.jogasoft.moviefinder.ui.screen.HomeScreen
import com.jogasoft.moviefinder.ui.screen.MovieDetailScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Serializable
data class MovieDetailScreen(val movieId: Int)

@Composable
fun MovieFinderAppNavigationHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HomeScreen) {
        composable<HomeScreen> {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                navigateToMovieDetail = { movieId ->
                    navController.navigate(MovieDetailScreen(movieId))
                }
            )
        }

        composable<MovieDetailScreen> {
            val viewModel = hiltViewModel<MovieDetailViewModel>()
            MovieDetailScreen(uiState = viewModel.uiState.collectAsStateWithLifecycle().value)
        }
    }
}
