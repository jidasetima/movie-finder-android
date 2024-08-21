package com.jogasoft.moviefinder.data.source.network

import com.jogasoft.moviefinder.data.source.network.model.movie.NetworkMovie
import com.jogasoft.moviefinder.data.source.network.model.movie.NetworkMoviePage
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.coroutines.cancellation.CancellationException
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class DefaultMovieNetworkDataSourceTest {
    private val networkMovies = List(20) { index ->
        NetworkMovie(
            id = index,
            adult = Random.nextBoolean(),
            backdropPath = "backdropPath.png",
            genreIds = listOf(),
            originalLanguage = "test language",
            originalTitle = " test original title",
            overview = "test overview",
            popularity = 1.0,
            posterPath = "posterPath.png",
            releaseDate = "2024-12-12",
            title = "fake title",
            video = false,
            voteAverage = 2.0,
            voteCount = 155
        )
    }

    private val networkMoviePage = NetworkMoviePage(
        page = 0,
        results = networkMovies,
        totalPages = 0,
        totalResults = networkMovies.size
    )

    private val movieApi = FakeMovieApi(networkMoviePage = networkMoviePage)

    private val movieNetworkDataSource = DefaultMovieNetworkDataSource(movieApi = movieApi)

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {
        movieApi.shouldReturnErrorResponse = false
    }

    @Test
    fun `getNowPlayingMovies returns success with expected result`() = runTest {
        val response = movieNetworkDataSource.getNowPlayingMovies()
        assertTrue(response.isSuccess)
        assertEquals(networkMoviePage.results, response.getOrNull())
    }

    @Test
    fun `getNowPlayingMovies returns failure with expected result`() = runTest {
        movieApi.shouldReturnErrorResponse = true
        val response = movieNetworkDataSource.getNowPlayingMovies()
        assertTrue(response.isFailure)
    }

    @Test
    fun `getNowPlayingMovies throws CancellationException when cancelled`() = runTest {
        movieApi.shouldThrowCancellationException = true
        assertFailsWith<CancellationException>(
            block = {
                movieNetworkDataSource.getNowPlayingMovies()
            }
        )
    }

    @Test
    fun `getPopularMovies returns success with expected result`() = runTest {
        val response = movieNetworkDataSource.getPopularMovies()
        assertTrue(response.isSuccess)
        assertEquals(networkMoviePage.results, response.getOrNull())
    }

    @Test
    fun `getPopularMovies returns failure with expected result`() = runTest {
        movieApi.shouldReturnErrorResponse = true
        val response = movieNetworkDataSource.getPopularMovies()
        assertTrue(response.isFailure)
    }

    @Test
    fun `getPopularMovies throws CancellationException when cancelled`() = runTest {
        movieApi.shouldThrowCancellationException = true
        assertFailsWith<CancellationException>(
            block = {
                movieNetworkDataSource.getPopularMovies()
            }
        )
    }

    @Test
    fun `getTopRatedMovies returns success with expected result`() = runTest {
        val response = movieNetworkDataSource.getTopRatedMovies()
        assertTrue(response.isSuccess)
        assertEquals(networkMoviePage.results, response.getOrNull())
    }

    @Test
    fun `getTopRatedMovies returns failure with expected result`() = runTest {
        movieApi.shouldReturnErrorResponse = true
        val response = movieNetworkDataSource.getTopRatedMovies()
        assertTrue(response.isFailure)
    }

    @Test
    fun `getTopRatedMovies throws CancellationException when cancelled`() = runTest {
        movieApi.shouldThrowCancellationException = true
        assertFailsWith<CancellationException>(
            block = {
                movieNetworkDataSource.getTopRatedMovies()
            }
        )
    }

    @Test
    fun `getUpcomingMovies returns success with expected result`() = runTest {
        val response = movieNetworkDataSource.getUpcomingMovies()
        assertTrue(response.isSuccess)
        assertEquals(networkMoviePage.results, response.getOrNull())
    }

    @Test
    fun `getUpcomingMovies returns failure with expected result`() = runTest {
        movieApi.shouldReturnErrorResponse = true
        val response = movieNetworkDataSource.getUpcomingMovies()
        assertTrue(response.isFailure)
    }

    @Test
    fun `getUpcomingMovies throws CancellationException when cancelled`() = runTest {
        movieApi.shouldThrowCancellationException = true
        assertFailsWith<CancellationException>(
            block = {
                movieNetworkDataSource.getUpcomingMovies()
            }
        )
    }
}