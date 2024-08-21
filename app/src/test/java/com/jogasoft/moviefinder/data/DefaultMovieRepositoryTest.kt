package com.jogasoft.moviefinder.data

import com.jogasoft.moviefinder.data.source.network.FakeMovieNetworkDataSource
import com.jogasoft.moviefinder.data.source.network.model.movie.NetworkMovie
import com.jogasoft.moviefinder.data.source.network.model.movie.toMovies
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.random.Random

class DefaultMovieRepositoryTest {
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

    private val movieNetworkDataSource = FakeMovieNetworkDataSource(networkMovies = networkMovies)
    private val movieRepository = DefaultMovieRepository(movieNetworkDataSource = movieNetworkDataSource)

    @Test
    fun `getNowPlayingMovies returns success with expected result`() = runTest {
        val response = movieRepository.getNowPlayingMovies()
        assertTrue(response.isSuccess)
        assertEquals(response.getOrNull(), networkMovies.toMovies())
    }

    @Test
    fun `getNowPlayingMovies returns failure with expected result`() = runTest {
        movieNetworkDataSource.shouldReturnFailureResult = true
        val response = movieRepository.getNowPlayingMovies()
        assertTrue(response.isFailure)
    }

    @Test
    fun `getPopularMovies returns success with expected result`() = runTest {
        val response = movieRepository.getPopularMovies()
        assertTrue(response.isSuccess)
        assertEquals(response.getOrNull(), networkMovies.toMovies())
    }

    @Test
    fun `getPopularMovies returns failure with expected result`() = runTest {
        movieNetworkDataSource.shouldReturnFailureResult = true
        val response = movieRepository.getPopularMovies()
        assertTrue(response.isFailure)
    }

    @Test
    fun `getTopRatedMovies returns success with expected result`() = runTest {
        val response = movieRepository.getTopRatedMovies()
        assertTrue(response.isSuccess)
        assertEquals(response.getOrNull(), networkMovies.toMovies())
    }

    @Test
    fun `getTopRatedMovies returns failure with expected result`() = runTest {
        movieNetworkDataSource.shouldReturnFailureResult = true
        val response = movieRepository.getTopRatedMovies()
        assertTrue(response.isFailure)
    }

    @Test
    fun `getUpcomingMovies returns success with expected result`() = runTest {
        val response = movieRepository.getUpcomingMovies()
        assertTrue(response.isSuccess)
        assertEquals(response.getOrNull(), networkMovies.toMovies())
    }

    @Test
    fun `getUpcomingMovies returns failure with expected result`() = runTest {
        movieNetworkDataSource.shouldReturnFailureResult = true
        val response = movieRepository.getUpcomingMovies()
        assertTrue(response.isFailure)
    }
}