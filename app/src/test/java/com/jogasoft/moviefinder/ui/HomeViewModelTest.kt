package com.jogasoft.moviefinder.ui

import com.jogasoft.moviefinder.data.FakeMovieRepository
import com.jogasoft.moviefinder.data.Movie
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
class HomeViewModelTest {
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

    private val movieRepository = FakeMovieRepository(movies = movies)
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
    fun `initializes uiState with nowPlayingMovies on ViewModel init`() = runTest {
        assertEquals(movies, homeViewModel.uiState.value.nowPlayingMovies)
    }
}
