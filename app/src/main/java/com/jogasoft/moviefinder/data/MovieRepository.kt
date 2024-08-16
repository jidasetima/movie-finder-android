package com.jogasoft.moviefinder.data

import com.jogasoft.moviefinder.data.source.network.MovieNetworkDataSource
import com.jogasoft.moviefinder.data.source.network.model.movie.toMovies
import kotlinx.coroutines.CancellationException

class MovieRepository {
    private val movieNetworkDataSource = MovieNetworkDataSource()

    suspend fun getNowPlayingMovies(): Result<List<Movie>> {
        return try {
            movieNetworkDataSource.getNowPlayingMovies().fold(
                onSuccess = { Result.success(it.toMovies()) },
                onFailure = { Result.failure(it) }
            )
        } catch (e: CancellationException) {
            throw e
        }
    }
}