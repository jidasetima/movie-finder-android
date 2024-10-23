package com.jogasoft.moviefinder.movie.ui.viewModel

import com.jogasoft.moviefinder.movie.data.Movie
import com.jogasoft.moviefinder.movie.data.MovieCategory
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

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {
    val movies = List(20) { index ->
        Movie(
            id = index,
            backdropPath = "/backdrop1.jpg",
            overview = "A thrilling adventure of a group of friends who find themselves trapped in a mysterious cave.",
            posterPath = "/poster1.jpg",
            releaseDate = "2023-07-15",
            title = "The Hidden Depths",
            category = MovieCategory.NOW_PLAYING
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

    private val movieRepository = FakeMovieRepository(
        movieDetail = movieDetail,
        movieList = movies
    )

    private lateinit var searchViewModel: SearchViewModel

    @Before
    fun setUp() {
        val testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        searchViewModel = SearchViewModel(movieRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initializes_searchTextState_with_correct_values() = runTest {
        val searchTextState = searchViewModel.searchTextState.value
        assertEquals("", searchTextState)
    }

    @Test
    fun updateSearchText_updates_searchTextState_with_correct_values() = runTest {
        searchViewModel.updateSearchText("test")
        assertEquals("test", searchViewModel.searchTextState.value)
    }
}