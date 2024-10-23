package com.jogasoft.moviefinder.movie.data.source.network

import com.jogasoft.moviefinder.movie.data.source.network.model.movie.NetworkMovie
import com.jogasoft.moviefinder.movie.data.source.network.model.movie.NetworkMoviePage
import com.jogasoft.moviefinder.movie.data.source.network.model.movieDetail.NetworkMovieDetail

class FakeMovieNetworkDataSource(
    private val networkMoviePage: NetworkMoviePage,
    private val networkMovieDetail: NetworkMovieDetail
): MovieNetworkDataSource {
    var shouldReturnFailureResult = false

    override suspend fun getNowPlayingMovies(): Result<List<NetworkMovie>> {
        return when {
            shouldReturnFailureResult -> Result.failure(Exception())
            else -> Result.success(networkMoviePage.results)
        }
    }

    override suspend fun getPopularMovies(): Result<List<NetworkMovie>> {
        return when {
            shouldReturnFailureResult -> Result.failure(Exception())
            else -> Result.success(networkMoviePage.results)
        }
    }

    override suspend fun getTopRatedMovies(): Result<List<NetworkMovie>> {
        return when {
            shouldReturnFailureResult -> Result.failure(Exception())
            else -> Result.success(networkMoviePage.results)
        }
    }

    override suspend fun getUpcomingMovies(): Result<List<NetworkMovie>> {
        return when {
            shouldReturnFailureResult -> Result.failure(Exception())
            else -> Result.success(networkMoviePage.results)
        }
    }

    override suspend fun paginateSearchedMovies(
        page: Int,
        query: String
    ): Result<NetworkMoviePage> {
        return when {
            shouldReturnFailureResult -> Result.failure(Exception())
            else -> Result.success(networkMoviePage)
        }
    }

    override suspend fun getMovieDetailById(movieId: Int): Result<NetworkMovieDetail> {
        return when {
            networkMovieDetail.id == movieId -> Result.success(networkMovieDetail)
            else -> Result.failure(Exception())
        }
    }
}