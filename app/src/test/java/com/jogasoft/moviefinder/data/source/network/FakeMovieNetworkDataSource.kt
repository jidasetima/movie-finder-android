package com.jogasoft.moviefinder.data.source.network

import com.jogasoft.moviefinder.data.source.network.model.movie.NetworkMovie

class FakeMovieNetworkDataSource(
    private val networkMovies: List<NetworkMovie>
): MovieNetworkDataSource {
    var shouldReturnFailureResult = false

    override suspend fun getNowPlayingMovies(): Result<List<NetworkMovie>> {
        return when {
            shouldReturnFailureResult -> Result.failure(Exception())
            else -> Result.success(networkMovies)
        }
    }
}