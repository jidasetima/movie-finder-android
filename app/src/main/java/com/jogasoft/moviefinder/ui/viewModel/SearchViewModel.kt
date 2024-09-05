package com.jogasoft.moviefinder.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jogasoft.moviefinder.data.Movie
import com.jogasoft.moviefinder.data.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private var _searchTextState = MutableStateFlow("")
    val searchTextState = _searchTextState.asStateFlow()

    private var _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _searchTextState.debounce(500)
                .filter { it.isNotBlank() }
                .collectLatest {
                    searchMovies()
                }
        }
    }

    fun updateSearchText(text: String) {
        _searchTextState.update { text }
    }

    private fun searchMovies() {
        viewModelScope.launch {
            movieRepository.getMoviesBySearchQuery(_searchTextState.value).fold(
                onSuccess = { movies ->
                    _uiState.update {
                        it.copy(
                            movies = movies
                        )
                    }
                },
                onFailure = {

                }
            )
        }
    }
}

data class SearchUiState(
    val movies: List<Movie> = listOf()
)