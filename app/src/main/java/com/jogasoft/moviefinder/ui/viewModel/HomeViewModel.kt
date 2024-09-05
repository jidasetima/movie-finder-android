package com.jogasoft.moviefinder.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jogasoft.moviefinder.data.Movie
import com.jogasoft.moviefinder.data.MovieCategory
import com.jogasoft.moviefinder.data.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    val uiState: StateFlow<HomeUiState> = movieRepository.observeMovies()
        .map { movies ->
            val groupedMovies = movies.groupBy { it.category }
            HomeUiState(
                nowPlayingMovies = groupedMovies[MovieCategory.NOW_PLAYING].orEmpty(),
                popularMovies = groupedMovies[MovieCategory.POPULAR].orEmpty(),
                topRatedMovies = groupedMovies[MovieCategory.TOP_RATED].orEmpty(),
                upcomingMovies = groupedMovies[MovieCategory.UPCOMING].orEmpty()
            )
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = HomeUiState(),
            started = SharingStarted.WhileSubscribed(5_000)
        )

    init {
        getNowPlayingMovieList()
        getPopularMovieList()
        getTopRatedMovieList()
        getUpcomingMovieList()
    }

    private fun getNowPlayingMovieList() {
        viewModelScope.launch {
            movieRepository.synchronizeNowPlayingMovies()
                .fold(
                    onSuccess = {},
                    onFailure = {
                        //todo: show error state
                    }
                )
        }
    }

    private fun getPopularMovieList() {
        viewModelScope.launch {
            movieRepository.synchronizePopularMovies().fold(
                onSuccess = {},
                onFailure = {
                    //todo: show error state
                }
            )
        }
    }

    private fun getTopRatedMovieList() {
        viewModelScope.launch {
            movieRepository.synchronizeTopRatedMovies().fold(
                onSuccess = {},
                onFailure = {
                    //todo: show error state
                }
            )
        }
    }

    private fun getUpcomingMovieList() {
        viewModelScope.launch {
            movieRepository.synchronizeUpcomingMovies().fold(
                onSuccess = {},
                onFailure = {
                    //todo: show error state
                }
            )
        }
    }
}

data class HomeUiState(
    val nowPlayingMovies: List<Movie> = listOf(),
    val popularMovies: List<Movie> = listOf(),
    val topRatedMovies: List<Movie> = listOf(),
    val upcomingMovies: List<Movie> = listOf(),
)