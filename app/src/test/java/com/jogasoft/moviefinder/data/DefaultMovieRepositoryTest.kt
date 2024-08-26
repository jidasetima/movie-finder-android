package com.jogasoft.moviefinder.data

import com.jogasoft.moviefinder.data.source.network.FakeMovieNetworkDataSource
import com.jogasoft.moviefinder.data.source.network.model.movie.NetworkMovie
import com.jogasoft.moviefinder.data.source.network.model.movie.toMovies
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkGenre
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkBelongsToCollection
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkMovieDetail
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkProductionCompany
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkProductionCountry
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkSpokenLanguage
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.toMovieDetail
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

    private val movieDetailId = 1
    private val movieDetail = NetworkMovieDetail(
        id = movieDetailId,
        adult = false,
        backdropPath = "Test Path",
        belongsToCollection = NetworkBelongsToCollection(
            id = 1,
            name = "Test Name",
            posterPath = "Test Path",
            backdropPath = "Test Path"
        ),
        budget = 200000000,
        genres = listOf(NetworkGenre(id = 1, name = "Action")),
        homepage = "www.test.com",
        imdbId = "1",
        originCountry = listOf("US"),
        originalLanguage = "en",
        originalTitle = "Test Title",
        overview = "Test Overview",
        popularity = 1.0,
        posterPath = "Test Path",
        productionCompanies = listOf(
            NetworkProductionCompany(
                id = 1,
                logoPath = "Test Path",
                name = "Test Name",
                originCountry = "US"
            ),
        ),
        productionCountries = listOf(
            NetworkProductionCountry(
                iso_3166_1 = "US",
                name = "United States of America"
            )
        ),
        releaseDate = "2024-07-24",
        revenue = 100,
        runtime = 1,
        spokenLanguages = listOf(
            NetworkSpokenLanguage(
                englishName = "English",
                iso_639_1 = "en",
                name = "English"
            )
        ),
        status = "Test Status",
        tagline = "Test TagLine",
        title = "Test Title",
        video = false,
        voteAverage = 1.0,
        voteCount = 1
    )

    private val movieNetworkDataSource = FakeMovieNetworkDataSource(
        networkMovies = networkMovies,
        networkMovieDetail = movieDetail
    )

    private val movieRepository = DefaultMovieRepository(movieNetworkDataSource = movieNetworkDataSource)

    @Test
    fun `getNowPlayingMovies returns success with expected result`() = runTest {
        val response = movieRepository.getNowPlayingMovies()
        assertTrue(response.isSuccess)
        assertEquals(networkMovies.toMovies(), response.getOrNull())
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
        assertEquals(networkMovies.toMovies(), response.getOrNull())
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
        assertEquals(networkMovies.toMovies(), response.getOrNull())
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
        assertEquals(networkMovies.toMovies(), response.getOrNull())
    }

    @Test
    fun `getUpcomingMovies returns failure with expected result`() = runTest {
        movieNetworkDataSource.shouldReturnFailureResult = true
        val response = movieRepository.getUpcomingMovies()
        assertTrue(response.isFailure)
    }

    @Test
    fun `getMovieDetailById returns success with expected result`() = runTest {
        val response = movieRepository.getMovieDetailById(movieDetailId)
        assertTrue(response.isSuccess)
        assertEquals(movieDetail.toMovieDetail(), response.getOrNull())
    }

    @Test
    fun `getMovieDetailById returns failure with expected result`() = runTest {
        val response = movieRepository.getMovieDetailById(movieDetailId + Random.nextInt())
        assertTrue(response.isFailure)
    }
}