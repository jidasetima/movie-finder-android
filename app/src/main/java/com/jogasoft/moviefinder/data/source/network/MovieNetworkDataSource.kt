package com.jogasoft.moviefinder.data.source.network

import android.util.Log
import com.jogasoft.moviefinder.BuildConfig
import com.jogasoft.moviefinder.data.source.network.model.movie.NetworkMovie
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.coroutines.cancellation.CancellationException

class MovieNetworkDataSource {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val movieApi: MovieApi = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_TMDB_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(MovieApi::class.java)

    suspend fun getNowPlayingMovies(): Result<List<NetworkMovie>> {
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
        private val TAG = MovieNetworkDataSource::class.java.simpleName
    }
}