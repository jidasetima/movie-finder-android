package com.jogasoft.moviefinder.data.source.network

import com.jogasoft.moviefinder.BuildConfig
import com.jogasoft.moviefinder.data.source.network.model.movie.NetworkMoviePage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface MovieApi {
    @Headers("Authorization: Bearer ${BuildConfig.TMDB_API_KEY}")
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): Response<NetworkMoviePage>
}