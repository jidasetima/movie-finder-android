package com.jogasoft.moviefinder.movie.data.source.network

import com.jogasoft.moviefinder.movie.data.source.network.model.movie.NetworkMoviePage
import com.jogasoft.moviefinder.movie.data.source.network.model.movieDetail.NetworkMovieDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): Response<NetworkMoviePage>

    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<NetworkMoviePage>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): Response<NetworkMoviePage>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): Response<NetworkMoviePage>

    @GET("search/movie")
    suspend fun paginateSearchedMovies(
        @Query("page") page: Int,
        @Query("query") query: String
    ): Response<NetworkMoviePage>

    @GET("movie/{movieId}")
    suspend fun getMovieDetailById(@Path("movieId") movieId: Int): Response<NetworkMovieDetail>
}