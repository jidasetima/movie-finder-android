package com.jogasoft.moviefinder.data

import com.jogasoft.moviefinder.data.source.local.FakeMovieDao
import com.jogasoft.moviefinder.data.source.network.FakeMovieNetworkDataSource
import com.jogasoft.moviefinder.data.source.network.model.movie.NetworkMovie
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkBelongsToCollection
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkGenre
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkMovieDetail
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkProductionCompany
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkProductionCountry
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkSpokenLanguage
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.toMovieDetail
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.random.Random

class DefaultMovieRepositoryTest {
    private val networkMovies = List(10) { index ->
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

    private val movieDao = FakeMovieDao()

    private val movieRepository = DefaultMovieRepository(
        movieNetworkDataSource = movieNetworkDataSource,
        movieDao = movieDao
    )

    @Test
    fun synchronizeNowPlayingMovies_returnsSuccess_withExpectedResult() = runTest {
        val response = movieRepository.synchronizeNowPlayingMovies()
        assertTrue(response.isSuccess)
        assertEquals(Unit, response.getOrNull())
    }

    @Test
    fun synchronizeNowPlayingMovies_returnsFailure_withExpectedResult() = runTest {
        movieNetworkDataSource.shouldReturnFailureResult = true
        val response = movieRepository.synchronizeNowPlayingMovies()
        assertTrue(response.isFailure)
    }

    @Test
    fun synchronizeNowPlayingMovies_updates_observeMovies_correctly() = runTest {
        val initialMovieList = movieRepository.observeMovies().first()
        assertEquals(listOf<Movie>(), initialMovieList)

        movieRepository.synchronizeNowPlayingMovies()

        val synchronizedMovieList = movieRepository.observeMovies().first()
        assertTrue(synchronizedMovieList.isNotEmpty())
        assertTrue(synchronizedMovieList.all { it.category == MovieCategory.NOW_PLAYING  })
    }

    @Test
    fun synchronizePopularMovies_returnsSuccess_withExpectedResult() = runTest {
        val response = movieRepository.synchronizePopularMovies()
        assertTrue(response.isSuccess)
        assertEquals(Unit, response.getOrNull())
    }

    @Test
    fun synchronizePopularMovies_returnsFailure_withExpectedResult() = runTest {
        movieNetworkDataSource.shouldReturnFailureResult = true
        val response = movieRepository.synchronizePopularMovies()
        assertTrue(response.isFailure)
    }

    @Test
    fun synchronizePopularMovies_updates_observeMovies_correctly() = runTest {
        val initialMovieList = movieRepository.observeMovies().first()
        assertEquals(listOf<Movie>(), initialMovieList)

        movieRepository.synchronizePopularMovies()

        val synchronizedMovieList = movieRepository.observeMovies().first()
        assertTrue(synchronizedMovieList.isNotEmpty())
        assertTrue(synchronizedMovieList.all { it.category == MovieCategory.POPULAR  })
    }

    @Test
    fun synchronizeTopRatedMovies_returnsSuccess_withExpectedResult() = runTest {
        val response = movieRepository.synchronizeTopRatedMovies()
        assertTrue(response.isSuccess)
        assertEquals(Unit, response.getOrNull())
    }

    @Test
    fun synchronizeTopRatedMovies_returnsFailure_withExpectedResult() = runTest {
        movieNetworkDataSource.shouldReturnFailureResult = true
        val response = movieRepository.synchronizeTopRatedMovies()
        assertTrue(response.isFailure)
    }

    @Test
    fun synchronizeTopRatedMovies_updates_observeMovies_correctly() = runTest {
        val initialMovieList = movieRepository.observeMovies().first()
        assertEquals(listOf<Movie>(), initialMovieList)

        movieRepository.synchronizeTopRatedMovies()

        val synchronizedMovieList = movieRepository.observeMovies().first()
        assertTrue(synchronizedMovieList.isNotEmpty())
        assertTrue(synchronizedMovieList.all { it.category == MovieCategory.TOP_RATED  })
    }

    @Test
    fun synchronizeUpcomingMovies_returnsSuccess_withExpectedResult() = runTest {
        val response = movieRepository.synchronizeUpcomingMovies()
        assertTrue(response.isSuccess)
        assertEquals(Unit, response.getOrNull())
    }

    @Test
    fun synchronizeUpcomingMovies_returnsFailure_withExpectedResult() = runTest {
        movieNetworkDataSource.shouldReturnFailureResult = true
        val response = movieRepository.synchronizeUpcomingMovies()
        assertTrue(response.isFailure)
    }

    @Test
    fun synchronizeUpcomingMovies_updates_observeMovies_correctly() = runTest {
        val initialMovieList = movieRepository.observeMovies().first()
        assertEquals(listOf<Movie>(), initialMovieList)

        movieRepository.synchronizeUpcomingMovies()

        val synchronizedMovieList = movieRepository.observeMovies().first()
        assertTrue(synchronizedMovieList.isNotEmpty())
        assertTrue(synchronizedMovieList.all { it.category == MovieCategory.UPCOMING  })
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