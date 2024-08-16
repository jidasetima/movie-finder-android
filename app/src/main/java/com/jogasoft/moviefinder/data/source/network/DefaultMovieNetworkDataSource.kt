package com.jogasoft.moviefinder.data.source.network

import android.util.Log
import com.jogasoft.moviefinder.data.source.network.model.movie.NetworkMovie
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class DefaultMovieNetworkDataSource @Inject constructor(
    private val movieApi: MovieApi
): MovieNetworkDataSource {

    override suspend fun getNowPlayingMovies(): Result<List<NetworkMovie>> {
        return try {
            val response = movieApi.getNowPlayingMovies()
            val moviePage = response.body()

            when {
                response.isSuccessful && moviePage != null -> {
                    Log.d(TAG, "Now Playing Movie Fetch successful")
                    Result.success(moviePage.results)
                }
                else -> {
                    Result.failure(
                        NetworkException(
                            message = response.errorBody()?.string() ?: "Failed to get Now Playing Movies",
                            responseCode = response.code(),
                        )
                    )
                }
            }
        } catch (e: CancellationException) {
            Log.e(TAG, "Get Now Playing Movies operation was canceled", e)
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get Now Playing Movies", e)
            Result.failure(e)
        }
    }

    companion object {
        private val TAG = DefaultMovieNetworkDataSource::class.java.simpleName
    }
}