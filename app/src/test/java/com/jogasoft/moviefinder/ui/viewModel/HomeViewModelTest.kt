package com.jogasoft.moviefinder.ui.viewModel

import com.jogasoft.moviefinder.data.FakeMovieRepository
import com.jogasoft.moviefinder.data.Movie
import com.jogasoft.moviefinder.data.MovieCategory
import com.jogasoft.moviefinder.data.MovieDetail
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    private val movies = MovieCategory.entries.mapIndexed { index, category ->
        Movie(
            id = index,
            backdropPath = "/backdrop1.jpg",
            overview = "Fake Overview",
            posterPath = "/poster1.jpg",
            releaseDate = "2023-07-15",
            title = "Fake title",
            category = category
        )
    }

    private val movieDetail = MovieDetail(
        id = 1,
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

    private val movieRepository = FakeMovieRepository(movieDetail = movieDetail)
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
    fun initializes_homeUiState_with_correct_values() {
        val homeUiState = HomeUiState()

        assertEquals(listOf<Movie>(), homeUiState.nowPlayingMovies)
        assertEquals(listOf<Movie>(), homeUiState.popularMovies)
        assertEquals(listOf<Movie>(), homeUiState.topRatedMovies)
        assertEquals(listOf<Movie>(), homeUiState.upcomingMovies)
    }

    @Test
    fun updates_uiState_nowPlayingMovies_correctly() = runTest {
        assertEquals(listOf<Movie>(), homeViewModel.uiState.value.nowPlayingMovies)
        val testJob = launch {
            homeViewModel.uiState.collect()
        }

        advanceUntilIdle()
        movieRepository.emit(movies)

        assertEquals(
            movies.filter { it.category == MovieCategory.NOW_PLAYING },
            homeViewModel.uiState.value.nowPlayingMovies
        )

        testJob.cancel()
        assertTrue(testJob.isCancelled)
    }

    @Test
    fun updates_uiState_popularMovies_correctly() = runTest {
        assertEquals(listOf<Movie>(), homeViewModel.uiState.value.popularMovies)
        val testJob = launch {
            homeViewModel.uiState.collect()
        }

        advanceUntilIdle()
        movieRepository.emit(movies)
        assertEquals(
            movies.filter { it.category == MovieCategory.POPULAR },
            homeViewModel.uiState.value.popularMovies
        )

        testJob.cancel()
        assertTrue(testJob.isCancelled)
    }

    @Test
    fun updates_uiState_topRatedMovies_correctly() = runTest {
        assertEquals(listOf<Movie>(), homeViewModel.uiState.value.topRatedMovies)
        val testJob = launch {
            homeViewModel.uiState.collect()
        }

        advanceUntilIdle()
        movieRepository.emit(movies)

        assertEquals(
            movies.filter { it.category == MovieCategory.TOP_RATED },
            homeViewModel.uiState.value.topRatedMovies
        )

        testJob.cancel()
        assertTrue(testJob.isCancelled)
    }

    @Test
    fun updates_uiState_upcomingMovies_correctly() = runTest {
        assertEquals(listOf<Movie>(), homeViewModel.uiState.value.upcomingMovies)
        val testJob = launch {
            homeViewModel.uiState.collect()
        }

        advanceUntilIdle()
        movieRepository.emit(movies)
        assertEquals(
            movies.filter { it.category == MovieCategory.UPCOMING },
            homeViewModel.uiState.value.upcomingMovies
        )

        testJob.cancel()
        assertTrue(testJob.isCancelled)
    }
}
