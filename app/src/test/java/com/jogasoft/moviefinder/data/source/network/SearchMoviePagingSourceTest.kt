package com.jogasoft.moviefinder.data.source.network

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import com.jogasoft.moviefinder.data.source.network.model.movie.NetworkMovie
import com.jogasoft.moviefinder.data.source.network.model.movie.NetworkMoviePage
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkBelongsToCollection
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkGenre
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkMovieDetail
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkProductionCompany
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkProductionCountry
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkSpokenLanguage
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SearchMoviePagingSourceTest {
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

    private val networkMoviePage = NetworkMoviePage(
        page = 1,
        results = networkMovies,
        totalPages = 1,
        totalResults = networkMovies.size
    )

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
        networkMoviePage = networkMoviePage,
        networkMovieDetail = movieDetail
    )

    private lateinit var pagingSource: SearchMoviePagingSource

    @Before
    fun setUp() {
        pagingSource = SearchMoviePagingSource(
            movieNetworkDataSource = movieNetworkDataSource,
            query = ""
        )
    }

    @Test
    fun returns_successfulPageLoad_with_expectedData() = runTest {
        val pager = TestPager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 3
            ),
            pagingSource = pagingSource
        )

        val result = pager.refresh() as PagingSource.LoadResult.Page

        assertEquals(result.data, networkMoviePage.results)
    }

    @Test
    fun returns_error_properly() = runTest {
        movieNetworkDataSource.shouldReturnFailureResult = true

        val pager = TestPager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 3
            ),
            pagingSource = pagingSource
        )

        val result = pager.refresh()
        assertTrue(result is PagingSource.LoadResult.Error)

        val page = pager.getLastLoadedPage()
        assertNull(page)
    }
}