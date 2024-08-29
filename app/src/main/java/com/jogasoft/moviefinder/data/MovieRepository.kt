package com.jogasoft.moviefinder.data

import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun observeMovies(): Flow<List<Movie>>
    suspend fun synchronizeNowPlayingMovies(): Result<Unit>
    suspend fun synchronizePopularMovies(): Result<Unit>
    suspend fun synchronizeTopRatedMovies(): Result<Unit>
    suspend fun synchronizeUpcomingMovies(): Result<Unit>
    suspend fun getMovieDetailById(movieId: Int): Result<MovieDetail>
}