package com.jogasoft.moviefinder.movie.data.local.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jogasoft.moviefinder.movie.data.MovieCategory
import com.jogasoft.moviefinder.movie.data.source.local.LocalMovie
import com.jogasoft.moviefinder.movie.data.source.local.database.MovieDao
import com.jogasoft.moviefinder.movie.data.source.local.database.MovieFinderDatabase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {
    private lateinit var movieDao: MovieDao
    private lateinit var db: MovieFinderDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, MovieFinderDatabase::class.java).build()
        movieDao = db.MovieDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun upsertAll_successfully_upserts_movies() = runTest {
        assertTrue(movieDao.observeMovies().first().isEmpty())

        val movies = List(10) { index ->
            LocalMovie(
                id = index,
                backdropPath = "backdropPath",
                overview = "overview",
                posterPath = "posterPath",
                releaseDate = "releaseDate",
                title = "title",
                category = MovieCategory.NOW_PLAYING // category is irrelevant for this test case
            )
        }

        movieDao.upsertAll(*movies.toTypedArray())

        val upsertedMovies = movieDao.observeMovies().first()
        assertEquals(movies, upsertedMovies)
    }

    @Test
    fun deleteMovieByCategory_successfully_deletes_movies_by_category() = runTest {
        assertTrue(movieDao.observeMovies().first().isEmpty())

        // create list of 1 movie per category
        val movies = MovieCategory.entries.mapIndexed { index, category ->
            LocalMovie(
                id = index,
                backdropPath = "backdropPath",
                overview = "overview",
                posterPath = "posterPath",
                releaseDate = "releaseDate",
                title = "title",
                category = category
            )
        }

        movieDao.upsertAll(*movies.toTypedArray())
        val currentDaoMovies = movieDao.observeMovies().first()
        assertEquals(movies, currentDaoMovies)

        var currentDaoMovieCount = currentDaoMovies.size
        MovieCategory.entries.forEach { category ->
            movieDao.deleteMovieByCategory(category)

            // movieDao should have 1 less movie now
            currentDaoMovieCount--

            val remainingDaoMovies = movieDao.observeMovies().first()
            assertEquals(currentDaoMovieCount, remainingDaoMovies.size)
            assertTrue(remainingDaoMovies.none { it.category == category })
        }
    }

    @Test
    fun clearAndInsertMoviesByCategory_successfully_clears_and_inserts_movies_by_category() = runTest {
        assertTrue(movieDao.observeMovies().first().isEmpty())

        // create list of 1 movie per category
        val initialDaoMovies = MovieCategory.entries.map { category ->
            LocalMovie(
                // random range applied to ensure different ids than the updated list
                id = Random.nextInt(0, 10),
                backdropPath = "initial",
                overview = "initial",
                posterPath = "initial",
                releaseDate = "initial",
                title = "initial",
                category = category
            )
        }

        movieDao.upsertAll(*initialDaoMovies.toTypedArray())
        assertEquals(initialDaoMovies, movieDao.observeMovies().first())

        // create list of 1 movie per category
        val updatedDaoMovies = MovieCategory.entries.map { category ->
            LocalMovie(
                // random range applied to ensure different ids than the initial list
                id = Random.nextInt(20, 30),
                backdropPath = "after",
                overview = "after",
                posterPath = "after",
                releaseDate = "after",
                title = "after",
                category = category
            )
        }

        MovieCategory.entries.forEach { category ->
            movieDao.clearAndInsertMoviesByCategory(
                movies = updatedDaoMovies.filter { it.category == category }.toTypedArray(),
                category = category
            )

            val initialMovieOfCategory = initialDaoMovies.firstOrNull { it.category == category }
            assertFalse(movieDao.observeMovies().first().contains(initialMovieOfCategory))

            val updatedMovieOfCategory = updatedDaoMovies.firstOrNull { it.category == category }
            assertTrue(movieDao.observeMovies().first().contains(updatedMovieOfCategory))
        }
    }
}