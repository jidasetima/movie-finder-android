package com.jogasoft.moviefinder.movie.data.source.network

import com.jogasoft.moviefinder.data.source.network.DefaultMovieNetworkDataSource
import com.jogasoft.moviefinder.data.source.network.model.movie.NetworkMovie
import com.jogasoft.moviefinder.data.source.network.model.movie.NetworkMoviePage
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkBelongsToCollection
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkGenre
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkMovieDetail
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkProductionCompany
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkProductionCountry
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkSpokenLanguage
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

    val movieDetailId = 1
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

    private val movieApi = FakeMovieApi(
        networkMoviePage = networkMoviePage,
        networkMovieDetail = movieDetail
    )

    private val movieNetworkDataSource = DefaultMovieNetworkDataSource(movieApi = movieApi)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
        movieApi.shouldReturnErrorResponse = false
    }

    @Test
    fun getNowPlayingMovies_returnsSuccess_withExpectedResult() = runTest {
        val response = movieNetworkDataSource.getNowPlayingMovies()
        assertTrue(response.isSuccess)
        assertEquals(networkMoviePage.results, response.getOrNull())
    }

    @Test
    fun getNowPlayingMovies_returnsFailure_withExpectedResult() = runTest {
        movieApi.shouldReturnErrorResponse = true
        val response = movieNetworkDataSource.getNowPlayingMovies()
        assertTrue(response.isFailure)
    }

    @Test
    fun getNowPlayingMovies_ThrowsCancellationException_WhenCancelled() = runTest {
        movieApi.shouldThrowCancellationException = true
        assertFailsWith<CancellationException> {
            movieNetworkDataSource.getNowPlayingMovies()
        }
    }

    @Test
    fun getPopularMovies_returnsSuccess_withExpectedResult() = runTest {
        val response = movieNetworkDataSource.getPopularMovies()
        assertTrue(response.isSuccess)
        assertEquals(networkMoviePage.results, response.getOrNull())
    }

    @Test
    fun getPopularMovies_returnsFailure_withExpectedResult() = runTest {
        movieApi.shouldReturnErrorResponse = true
        val response = movieNetworkDataSource.getPopularMovies()
        assertTrue(response.isFailure)
    }

    @Test
    fun getPopularMovies_ThrowsCancellationException_whenCancelled() = runTest {
        movieApi.shouldThrowCancellationException = true
        assertFailsWith<CancellationException> {
            movieNetworkDataSource.getPopularMovies()
        }
    }

    @Test
    fun getTopRatedMovies_returnsSuccess_withExpectedResult() = runTest {
        val response = movieNetworkDataSource.getTopRatedMovies()
        assertTrue(response.isSuccess)
        assertEquals(networkMoviePage.results, response.getOrNull())
    }

    @Test
    fun getTopRatedMovies_returnsFailure_withExpectedResult() = runTest {
        movieApi.shouldReturnErrorResponse = true
        val response = movieNetworkDataSource.getTopRatedMovies()
        assertTrue(response.isFailure)
    }

    @Test
    fun getTopRatedMovies_throwsCancellationException_whenCancelled() = runTest {
        movieApi.shouldThrowCancellationException = true
        assertFailsWith<CancellationException> {
            movieNetworkDataSource.getTopRatedMovies()
        }
    }

    @Test
    fun getUpcomingMovies_returnsSuccess_withExpectedResult() = runTest {
        val response = movieNetworkDataSource.getUpcomingMovies()
        assertTrue(response.isSuccess)
        assertEquals(networkMoviePage.results, response.getOrNull())
    }

    @Test
    fun getUpcomingMovies_returnsFailure_withExpectedResult() = runTest {
        movieApi.shouldReturnErrorResponse = true
        val response = movieNetworkDataSource.getUpcomingMovies()
        assertTrue(response.isFailure)
    }

    @Test
    fun getUpcomingMovies_throwsCancellationException_whenCancelled() = runTest {
        movieApi.shouldThrowCancellationException = true
        assertFailsWith<CancellationException> {
            movieNetworkDataSource.getUpcomingMovies()
        }
    }

    @Test
    fun searchMovies_returnsSuccess_withExpectedResult() = runTest {
        val searchQuery = "a"
        val response = movieNetworkDataSource.paginateSearchedMovies(
            networkMoviePage.page,
            searchQuery
        )

        assertEquals(
            networkMoviePage,
            response.getOrNull()
        )
    }

    @Test
    fun searchMovies_returnsFailure_withExpectedResult() = runTest {
        val searchQuery = "a"
        movieApi.shouldReturnErrorResponse = true
        val response = movieNetworkDataSource.paginateSearchedMovies(
            networkMoviePage.page,
            searchQuery
        )
        assertTrue(response.isFailure)
    }

    @Test
    fun searchMovies_throwsCancellationException_whenCancelled() = runTest {
        val searchQuery = "a"
        movieApi.shouldThrowCancellationException = true
        assertFailsWith<CancellationException> {
            movieNetworkDataSource.paginateSearchedMovies(
                networkMoviePage.page,
                searchQuery
            )
        }
    }

    @Test
    fun getMovieDetailById_returnsSuccess_withExpectedResult() = runTest {
        val response = movieNetworkDataSource.getMovieDetailById(movieDetailId)
        assertTrue(response.isSuccess)
        assertEquals(movieDetail, response.getOrNull())
    }

    @Test
    fun getMovieDetailById_returnsFailure_withExpectedResult() = runTest {
        movieApi.shouldReturnErrorResponse = true
        val response = movieNetworkDataSource.getMovieDetailById(movieDetailId)
        assertTrue(response.isFailure)
    }

    @Test
    fun getMovieDetailById_throwsCancellationException_whenCancelled() = runTest {
        movieApi.shouldThrowCancellationException = true
        assertFailsWith<CancellationException> {
            movieNetworkDataSource.getMovieDetailById(movieDetailId)
        }
    }
}