package com.jogasoft.moviefinder.data

import com.jogasoft.moviefinder.data.source.network.DefaultMovieNetworkDataSource
import com.jogasoft.moviefinder.data.source.network.model.movie.toMovies
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class DefaultMovieRepository @Inject constructor(
    private val movieNetworkDataSource: DefaultMovieNetworkDataSource
) : MovieRepository {
    override suspend fun getNowPlayingMovies(): Result<List<Movie>> {
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