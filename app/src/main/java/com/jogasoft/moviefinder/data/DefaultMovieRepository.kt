package com.jogasoft.moviefinder.data

import com.jogasoft.moviefinder.data.source.network.MovieNetworkDataSource
import com.jogasoft.moviefinder.data.source.network.model.movie.toMovies
import javax.inject.Inject

class DefaultMovieRepository @Inject constructor(
    private val movieNetworkDataSource: MovieNetworkDataSource
) : MovieRepository {
    override suspend fun getNowPlayingMovies(): Result<List<Movie>> {
        return movieNetworkDataSource.getNowPlayingMovies().fold(
            onSuccess = { Result.success(it.toMovies()) },
            onFailure = { Result.failure(it) }
        )
    }

    override suspend fun getPopularMovies(): Result<List<Movie>> {
        return movieNetworkDataSource.getPopularMovies().fold(
            onSuccess = { Result.success(it.toMovies()) },
            onFailure = { Result.failure(it) }
        )
    }

    override suspend fun getTopRatedMovies(): Result<List<Movie>> {
        return movieNetworkDataSource.getTopRatedMovies().fold(
            onSuccess = { Result.success(it.toMovies()) },
            onFailure = { Result.failure(it) }
        )
    }

    override suspend fun getUpcomingMovies(): Result<List<Movie>> {
        return movieNetworkDataSource.getUpcomingMovies().fold(
            onSuccess = { Result.success(it.toMovies()) },
            onFailure = { Result.failure(it) }
        )
    }
}