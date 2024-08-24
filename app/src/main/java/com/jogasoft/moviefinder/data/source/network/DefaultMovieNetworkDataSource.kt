package com.jogasoft.moviefinder.data.source.network

import android.util.Log
import com.jogasoft.moviefinder.data.source.network.model.movie.NetworkMovie
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkMovieDetail
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class DefaultMovieNetworkDataSource @Inject constructor(
    private val movieApi: MovieApi
) : MovieNetworkDataSource {
    enum class MovieRequestType(val value: String) {
        NOW_PLAYING("Now Playing"),
        POPULAR("Popular"),
        TOP_RATED("Top Rated"),
        UPCOMING("Upcoming")
    }

    override suspend fun getNowPlayingMovies(): Result<List<NetworkMovie>> {
        return getMovieListByType(MovieRequestType.NOW_PLAYING)
    }

    override suspend fun getPopularMovies(): Result<List<NetworkMovie>> {
        return getMovieListByType(MovieRequestType.POPULAR)
    }

    override suspend fun getTopRatedMovies(): Result<List<NetworkMovie>> {
        return getMovieListByType(MovieRequestType.TOP_RATED)
    }

    override suspend fun getUpcomingMovies(): Result<List<NetworkMovie>> {
        return getMovieListByType(MovieRequestType.UPCOMING)
    }

    override suspend fun getMovieDetailById(movieId: Int): Result<NetworkMovieDetail> {
        return try {
            val response = movieApi.getMovieDetailById(movieId)
            val movieDetail = response.body()
            if (response.isSuccessful && movieDetail != null) {
                Result.success(movieDetail)
            } else {
                Result.failure(
                    NetworkException(
                        message = response.errorBody()?.string()
                            ?: "Failed to get Movie Detail By Id $movieId",
                        responseCode = response.code()
                    )
                )
            }
        } catch (e: CancellationException) {
            Log.e(TAG, "Get Movie detail for id: $movieId operation was canceled", e)
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get Movie Detail By Id $movieId")
            Result.failure(e)
        }
    }

    private suspend fun getMovieListByType(type: MovieRequestType): Result<List<NetworkMovie>> {
        return try {
            val response = when (type) {
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
                            message = response.errorBody()?.string()
                                ?: "Failed to get ${type.value} Movies",
                            responseCode = response.code(),
                        )
                    )
                }
            }
        } catch (e: CancellationException) {
            Log.e(TAG, "Get ${type.value} Movies operation was canceled", e)
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get ${type.value} Movies", e)
            Result.failure(e)
        }
    }

    companion object {
        private val TAG = DefaultMovieNetworkDataSource::class.java.simpleName
    }
}