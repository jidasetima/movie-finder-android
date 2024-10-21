package com.jogasoft.moviefinder.movie.ui.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jogasoft.moviefinder.data.MovieDetail
import com.jogasoft.moviefinder.data.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedSateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val movieId: Int = checkNotNull(savedSateHandle["movieId"])
    private var _uiState: MutableStateFlow<MovieDetailUiState> = MutableStateFlow(MovieDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getMovieDetails()
    }

    private fun getMovieDetails() {
        viewModelScope.launch {
            movieRepository.getMovieDetailById(movieId = movieId).fold(
                onSuccess = { movieDetail ->
                    _uiState.update {
                        when(it) {
                            is MovieDetailUiState.Loading -> MovieDetailUiState.Success(movieDetail = movieDetail)
                            is MovieDetailUiState.Success -> { it.copy(movieDetail = movieDetail) }
                        }
                    }
                },
                onFailure = {
                    //todo: handle failure
                }
            )
        }
    }
}

sealed class MovieDetailUiState {
    data object Loading : MovieDetailUiState()

    data class Success(
        val movieDetail: MovieDetail
    ): MovieDetailUiState()
}
