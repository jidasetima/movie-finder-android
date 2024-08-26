package com.jogasoft.moviefinder.data.source.network

import com.jogasoft.moviefinder.data.source.network.model.movie.NetworkMoviePage
import com.jogasoft.moviefinder.data.source.network.model.movieDetail.NetworkMovieDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): Response<NetworkMoviePage>

    @GET("movie/popular?language=en-US&page=1")
    suspend fun getPopularMovies(): Response<NetworkMoviePage>

    @GET("movie/top_rated?language=en-US&page=1")
    suspend fun getTopRatedMovies(): Response<NetworkMoviePage>

    @GET("movie/upcoming?language=en-US&page=1")
    suspend fun getUpcomingMovies(): Response<NetworkMoviePage>

    @GET("movie/{movieId}")
    suspend fun getMovieDetailById(@Path("movieId") movieId: Int): Response<NetworkMovieDetail>
}