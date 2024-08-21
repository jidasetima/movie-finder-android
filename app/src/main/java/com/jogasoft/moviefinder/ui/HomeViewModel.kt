package com.jogasoft.moviefinder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jogasoft.moviefinder.data.Movie
import com.jogasoft.moviefinder.data.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getNowPlayingMovieList()
        getPopularMovieList()
        getTopRatedMovieList()
        getUpcomingMovieList()
    }

    private fun getNowPlayingMovieList() {
        viewModelScope.launch {
            movieRepository.getNowPlayingMovies().fold(
                onSuccess = { movieList ->
                    _uiState.update {
                        it.copy(
                            nowPlayingMovies = movieList
                        )
                    }
                },
                onFailure = {
                    //todo: show error state
                }
            )
        }
    }

    private fun getPopularMovieList() {
        viewModelScope.launch {
            movieRepository.getPopularMovies().fold(
                onSuccess = { movieList ->
                    _uiState.update {
                        it.copy(
                            popularMovies = movieList
                        )
                    }
                },
                onFailure = {
                    //todo: show error state
                }
            )
        }
    }

    private fun getTopRatedMovieList() {
        viewModelScope.launch {
            movieRepository.getTopRatedMovies().fold(
                onSuccess = { movieList ->
                    _uiState.update {
                        it.copy(
                            topRatedMovies = movieList
                        )
                    }
                },
                onFailure = {
                    //todo: show error state
                }
            )
        }
    }

    private fun getUpcomingMovieList() {
        viewModelScope.launch {
            movieRepository.getUpcomingMovies().fold(
                onSuccess = { movieList ->
                    _uiState.update {
                        it.copy(
                            upcomingMovies = movieList
                        )
                    }
                },
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