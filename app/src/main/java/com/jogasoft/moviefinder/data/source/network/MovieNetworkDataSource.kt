package com.jogasoft.moviefinder.data.source.network

import com.jogasoft.moviefinder.data.source.network.model.movie.NetworkMovie

interface MovieNetworkDataSource {
    suspend fun getNowPlayingMovies(): Result<List<NetworkMovie>>
}