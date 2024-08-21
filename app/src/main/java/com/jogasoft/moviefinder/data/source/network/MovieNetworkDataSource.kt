package com.jogasoft.moviefinder.data.source.network

import com.jogasoft.moviefinder.data.source.network.model.movie.NetworkMovie

interface MovieNetworkDataSource {
    suspend fun getNowPlayingMovies(): Result<List<NetworkMovie>>
    suspend fun getPopularMovies(): Result<List<NetworkMovie>>
    suspend fun getTopRatedMovies(): Result<List<NetworkMovie>>
    suspend fun getUpcomingMovies(): Result<List<NetworkMovie>>
}