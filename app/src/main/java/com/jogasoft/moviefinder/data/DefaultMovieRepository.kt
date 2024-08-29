package com.jogasoft.moviefinder.data

import com.jogasoft.moviefinder.data.source.local.database.MovieDao
import com.jogasoft.moviefinder.data.source.local.toMovies
import com.jogasoft.moviefinder.data.source.network.MovieNetworkDataSource
import com.jogasoft.moviefinder.data.source.network.model.movie.toLocalMovies
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.toMovieDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultMovieRepository @Inject constructor(
    private val movieDao: MovieDao,
    private val movieNetworkDataSource: MovieNetworkDataSource
) : MovieRepository {
    override fun observeMovies(): Flow<List<Movie>> = movieDao.observeMovies().map { it.toMovies() }

    override suspend fun synchronizeNowPlayingMovies(): Result<Unit> {
        return movieNetworkDataSource.getNowPlayingMovies().fold(
            onSuccess = {
                val movies = it.toLocalMovies(MovieCategory.NOW_PLAYING)
                movieDao.clearAndInsertMoviesByCategory(
                    movies = movies.toTypedArray(),
                    category = MovieCategory.NOW_PLAYING
                )
                Result.success(Unit)
            },
            onFailure = { Result.failure(it) }
        )
    }

    override suspend fun synchronizePopularMovies(): Result<Unit> {
        return movieNetworkDataSource.getPopularMovies().fold(
            onSuccess = {
                val movies = it.toLocalMovies(MovieCategory.POPULAR)
                movieDao.clearAndInsertMoviesByCategory(
                    movies = movies.toTypedArray(),
                    category = MovieCategory.POPULAR
                )
                Result.success(Unit)
            },
            onFailure = { Result.failure(it) }
        )
    }

    override suspend fun synchronizeTopRatedMovies(): Result<Unit> {
        return movieNetworkDataSource.getTopRatedMovies().fold(
            onSuccess = {
                val movies = it.toLocalMovies(MovieCategory.TOP_RATED)
                movieDao.clearAndInsertMoviesByCategory(
                    movies = movies.toTypedArray(),
                    category = MovieCategory.TOP_RATED
                )
                Result.success(Unit)
            },
            onFailure = { Result.failure(it) }
        )
    }

    override suspend fun synchronizeUpcomingMovies(): Result<Unit> {
        return movieNetworkDataSource.getUpcomingMovies().fold(
            onSuccess = {
                val movies = it.toLocalMovies(MovieCategory.UPCOMING)
                movieDao.clearAndInsertMoviesByCategory(
                    movies = movies.toTypedArray(),
                    category = MovieCategory.UPCOMING
                )
                Result.success(Unit)
            },
            onFailure = { Result.failure(it) }
        )
    }

    override suspend fun getMovieDetailById(movieId: Int): Result<MovieDetail> {
        return movieNetworkDataSource.getMovieDetailById(movieId).fold(
            onSuccess = { Result.success(it.toMovieDetail()) },
            onFailure = { Result.failure(it) }
        )
    }
}