package com.jogasoft.moviefinder.ui

import androidx.lifecycle.SavedStateHandle
import com.jogasoft.moviefinder.data.FakeMovieRepository
import com.jogasoft.moviefinder.data.Movie
import com.jogasoft.moviefinder.data.MovieDetail
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
    private val movies = List(20) { index: Int ->
        Movie(
            id = index,
            backdropPath = "/backdrop1.jpg",
            overview = "Fake Overview",
            posterPath = "/poster1.jpg",
            releaseDate = "2023-07-15",
            title = "Fake title"
        )
    }

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
    private val movieRepository = FakeMovieRepository(movies = movies, movieDetail = movieDetail)

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