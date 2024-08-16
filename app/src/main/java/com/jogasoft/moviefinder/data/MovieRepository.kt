package com.jogasoft.moviefinder.data

interface MovieRepository {
    suspend fun getNowPlayingMovies(): Result<List<Movie>>
}