package com.jogasoft.moviefinder.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jogasoft.moviefinder.data.Movie
import com.jogasoft.moviefinder.data.MovieRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val movieRepository = MovieRepository()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("HomeViewModel", "Coroutine Failed", throwable)
    }
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getNowPlayingMovieList()
    }

    private fun getNowPlayingMovieList() {
        viewModelScope.launch(exceptionHandler) {
            val response = movieRepository.getNowPlayingMovies()
            response.onSuccess { movieList ->
                _uiState.update {
                    it.copy(
                        nowPlayingMovies = movieList
                    )
                }
            }

            response.onFailure {
                //todo: show error state
            }
        }
    }
}

data class HomeUiState(
    val nowPlayingMovies: List<Movie> = listOf()
)