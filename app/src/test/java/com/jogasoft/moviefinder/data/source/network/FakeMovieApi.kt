package com.jogasoft.moviefinder.data.source.network

import com.jogasoft.moviefinder.data.source.network.model.movie.NetworkMoviePage
import retrofit2.Response
import kotlin.coroutines.cancellation.CancellationException

class FakeMovieApi(private val networkMoviePage: NetworkMoviePage): MovieApi {
    var shouldThrowCancellationException = false
    var shouldReturnErrorResponse = false

    override suspend fun getNowPlayingMovies(): Response<NetworkMoviePage> {
        return when {
            shouldThrowCancellationException -> throw CancellationException("Simulated Cancellation")
            shouldReturnErrorResponse -> {
                Response.error(500, null)
            }

            else -> Response.success(networkMoviePage)
        }
    }
}