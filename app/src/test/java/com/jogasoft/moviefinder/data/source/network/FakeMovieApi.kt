package com.jogasoft.moviefinder.data.source.network

import com.jogasoft.moviefinder.data.source.network.model.movie.NetworkMoviePage
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkMovieDetail
import retrofit2.Response
import kotlin.coroutines.cancellation.CancellationException

class FakeMovieApi(
    private val networkMoviePage: NetworkMoviePage,
    private val networkMovieDetail: NetworkMovieDetail
) : MovieApi {
    var shouldThrowCancellationException = false
    var shouldReturnErrorResponse = false

    private fun getMovies(): Response<NetworkMoviePage> {
        return when {
            shouldThrowCancellationException -> throw CancellationException("Simulated Cancellation")
            shouldReturnErrorResponse -> Response.error(500, null)
            else -> Response.success(networkMoviePage)
        }
    }

    override suspend fun getNowPlayingMovies(): Response<NetworkMoviePage> = getMovies()

    override suspend fun getPopularMovies(): Response<NetworkMoviePage> = getMovies()

    override suspend fun getTopRatedMovies(): Response<NetworkMoviePage> = getMovies()

    override suspend fun getUpcomingMovies(): Response<NetworkMoviePage> = getMovies()

    override suspend fun searchMovies(query: String): Response<NetworkMoviePage> {
        return when {
            shouldThrowCancellationException -> throw CancellationException("Simulated Cancellation")
            shouldReturnErrorResponse -> Response.error(500, null)
            else -> {
                val filteredResults = networkMoviePage.results.filter { it.title.contains(query) }
                val filteredNetworkMoviePage = networkMoviePage.copy(
                    results =  filteredResults,
                    totalResults = filteredResults.size
                )

                return Response.success(filteredNetworkMoviePage)
            }
        }
    }

    override suspend fun getMovieDetailById(movieId: Int): Response<NetworkMovieDetail> {
        return when {
            shouldThrowCancellationException -> throw CancellationException("Simulated Cancellation")
            shouldReturnErrorResponse -> Response.error(500, null)
            else -> Response.success(networkMovieDetail)
        }
    }
}