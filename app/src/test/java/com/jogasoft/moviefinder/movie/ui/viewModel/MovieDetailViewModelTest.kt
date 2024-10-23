package com.jogasoft.moviefinder.movie.ui.viewModel

import androidx.lifecycle.SavedStateHandle
import com.jogasoft.moviefinder.movie.data.MovieDetail
import com.jogasoft.moviefinder.movie.data.FakeMovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailViewModelTest{
    private val movieDetailId = 1
    private val movieDetail = MovieDetail(
        id = movieDetailId,
        backdropPath = "Fake Path",
        budget = 1,
        genres = listOf(),
        overview = "Fake Overview",
        popularity = 1.0,
        posterPath = "Fake Path",
        releaseDate = "Fake Date",
        runtime = 1,
        tagline = "Fake Tagline",
        title = "Fake Title"
    )

    private val savedStateHandle = SavedStateHandle(mapOf("movieId" to movieDetailId))
    private val movieRepository = FakeMovieRepository(movieDetail = movieDetail)

    private lateinit var movieDetailViewModel: MovieDetailViewModel

    @Before
    fun setUp() {
        val testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        movieDetailViewModel = MovieDetailViewModel(
            savedSateHandle = savedStateHandle,
            movieRepository = movieRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `savedStateHandle movieId matches UiState id after initialization`() = runTest {
        val uiStateValue = movieDetailViewModel.uiState.value
        assertTrue(uiStateValue is MovieDetailUiState.Success)
        assertEquals(movieDetailId, uiStateValue.movieDetail.id)
    }

    @Test
    fun `updates uiState with movie details on ViewModel init`() = runTest {
        val uiStateValue = movieDetailViewModel.uiState.value
        assertTrue(uiStateValue is MovieDetailUiState.Success)
        assertEquals(movieDetail, uiStateValue.movieDetail)
    }
}