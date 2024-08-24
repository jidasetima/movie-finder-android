package com.jogasoft.moviefinder.ui

import com.jogasoft.moviefinder.data.FakeMovieRepository
import com.jogasoft.moviefinder.data.Movie
import com.jogasoft.moviefinder.data.MovieDetail
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModelTest {
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

    private val movieDetail = MovieDetail(
        id = 1,
        backdropPath = "Fake Path",
        budget = 1,
        overview = "Fake Overview",
        popularity = 1.0,
        posterPath = "Fake Path",
        releaseDate = "Fake Date",
        runtime = 1,
        tagline = "Fake Tagline",
        title = "Fake Title"
    )

    private val movieRepository = FakeMovieRepository(movies = movies, movieDetail = movieDetail)
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        val testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        homeViewModel = HomeViewModel(movieRepository = movieRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `HomeUiState initializes with correct default values`() {
        val homeUiState = HomeUiState()

        assertEquals(listOf<Movie>(), homeUiState.nowPlayingMovies)
        assertEquals(listOf<Movie>(), homeUiState.popularMovies)
        assertEquals(listOf<Movie>(), homeUiState.topRatedMovies)
        assertEquals(listOf<Movie>(), homeUiState.upcomingMovies)
    }

    @Test
    fun `updates uiState with now playing movies on ViewModel init`() = runTest {
        assertEquals(movies, homeViewModel.uiState.value.nowPlayingMovies)
    }

    @Test
    fun `updates uiState with popular movies on ViewModel init`() = runTest {
        assertEquals(movies, homeViewModel.uiState.value.popularMovies)
    }

    @Test
    fun `updates uiState with top rated movies on ViewModel init`() = runTest {
        assertEquals(movies, homeViewModel.uiState.value.topRatedMovies)
    }

    @Test
    fun `updates uiState with upcoming movies on ViewModel init`() = runTest {
        assertEquals(movies, homeViewModel.uiState.value.upcomingMovies)
    }
}
