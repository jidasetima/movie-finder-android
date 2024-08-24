package com.jogasoft.moviefinder.data

interface MovieRepository {
    suspend fun getNowPlayingMovies(): Result<List<Movie>>
    suspend fun getPopularMovies(): Result<List<Movie>>
    suspend fun getTopRatedMovies(): Result<List<Movie>>
    suspend fun getUpcomingMovies(): Result<List<Movie>>
    suspend fun getMovieDetailById(movieId: Int): Result<MovieDetail>
}