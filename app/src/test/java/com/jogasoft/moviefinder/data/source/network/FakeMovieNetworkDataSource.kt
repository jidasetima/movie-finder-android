package com.jogasoft.moviefinder.data.source.network

import com.jogasoft.moviefinder.data.source.network.model.movie.NetworkMovie
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkMovieDetail

class FakeMovieNetworkDataSource(
    private val networkMovies: List<NetworkMovie>,
    private val networkMovieDetail: NetworkMovieDetail
): MovieNetworkDataSource {
    var shouldReturnFailureResult = false

    override suspend fun getNowPlayingMovies(): Result<List<NetworkMovie>> {
        return when {
            shouldReturnFailureResult -> Result.failure(Exception())
            else -> Result.success(networkMovies)
        }
    }

    override suspend fun getPopularMovies(): Result<List<NetworkMovie>> {
        return when {
            shouldReturnFailureResult -> Result.failure(Exception())
            else -> Result.success(networkMovies)
        }
    }

    override suspend fun getTopRatedMovies(): Result<List<NetworkMovie>> {
        return when {
            shouldReturnFailureResult -> Result.failure(Exception())
            else -> Result.success(networkMovies)
        }
    }

    override suspend fun getUpcomingMovies(): Result<List<NetworkMovie>> {
        return when {
            shouldReturnFailureResult -> Result.failure(Exception())
            else -> Result.success(networkMovies)
        }
    }

    override suspend fun searchMovies(query: String): Result<List<NetworkMovie>> {
        return when {
            shouldReturnFailureResult -> Result.failure(Exception())
            else -> Result.success(networkMovies)
        }
    }

    override suspend fun getMovieDetailById(movieId: Int): Result<NetworkMovieDetail> {
        return when {
            networkMovieDetail.id == movieId -> Result.success(networkMovieDetail)
            else -> Result.failure(Exception())
        }
    }
}