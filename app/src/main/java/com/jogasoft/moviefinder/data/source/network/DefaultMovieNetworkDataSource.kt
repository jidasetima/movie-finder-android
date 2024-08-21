package com.jogasoft.moviefinder.data.source.network

import android.util.Log
import com.jogasoft.moviefinder.data.source.network.model.movie.NetworkMovie
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class DefaultMovieNetworkDataSource @Inject constructor(
    private val movieApi: MovieApi
): MovieNetworkDataSource {
    enum class MovieRequestType(val value: String) {
        NOW_PLAYING("Now Playing"),
        POPULAR("Popular"),
        TOP_RATED("Top Rated"),
        UPCOMING("Upcoming")
    }

    override suspend fun getNowPlayingMovies(): Result<List<NetworkMovie>> {
        return getMoviesByType(MovieRequestType.NOW_PLAYING)
    }

    override suspend fun getPopularMovies(): Result<List<NetworkMovie>> {
        return getMoviesByType(MovieRequestType.POPULAR)
    }

    override suspend fun getTopRatedMovies(): Result<List<NetworkMovie>> {
        return getMoviesByType(MovieRequestType.TOP_RATED)
    }

    override suspend fun getUpcomingMovies(): Result<List<NetworkMovie>> {
        return getMoviesByType(MovieRequestType.UPCOMING)
    }

    private suspend fun getMoviesByType(type: MovieRequestType): Result<List<NetworkMovie>> {
        return try {
            val response =  when(type) {
                MovieRequestType.NOW_PLAYING -> movieApi.getNowPlayingMovies()
                MovieRequestType.POPULAR -> movieApi.getPopularMovies()
                MovieRequestType.TOP_RATED -> movieApi.getTopRatedMovies()
                MovieRequestType.UPCOMING -> movieApi.getUpcomingMovies()
            }

            val moviePage = response.body()

            when {
                response.isSuccessful && moviePage != null -> {
                    Log.d(TAG, "${type.value} Movie Fetch successful")
                    Result.success(moviePage.results)
                }
                else -> {
                    Result.failure(
                        NetworkException(
                            message = response.errorBody()?.string() ?: "Failed to get ${type.value}  Movies",
                            responseCode = response.code(),
                        )
                    )
                }
            }
        } catch (e: CancellationException) {
            Log.e(TAG, "Get ${type.value}  Movies operation was canceled", e)
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get ${type.value}  Movies", e)
            Result.failure(e)
        }
    }

    companion object {
        private val TAG = DefaultMovieNetworkDataSource::class.java.simpleName
    }
}