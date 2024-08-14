package com.jogasoft.moviefinder.data

import android.util.Log
import com.jogasoft.moviefinder.data.source.network.MovieNetworkDataSource
import com.jogasoft.moviefinder.data.source.network.NetworkException
import com.jogasoft.moviefinder.data.source.network.model.movie.toMovies
import kotlinx.coroutines.CancellationException

class MovieRepository {
    private val movieNetworkDataSource = MovieNetworkDataSource()

    suspend fun getNowPlayingMovies(): Result<List<Movie>> {
        return try {
            val response = movieNetworkDataSource.movieApi.getNowPlayingMovies()
            val moviePage = response.body()
            if (response.isSuccessful && moviePage != null) {
                Log.d("MovieRepository", "Now Playing Movie Fetch successful")
                Result.success(moviePage.results.toMovies())
            } else {
                Result.failure(
                    NetworkException(
                        message = response.errorBody()?.string() ?: "Failed to get Now Playing Movies",
                        responseCode = response.code(),
                    )
                )
            }
        } catch (e: CancellationException) {
            Log.e("MovieRepository", "Failed to get Now Playing Movies", e)
            throw e
        } catch (e: Exception) {
            Log.e("MovieRepository", "Failed to get Now Playing Movies", e)
            Result.failure(e)
        }
    }
}