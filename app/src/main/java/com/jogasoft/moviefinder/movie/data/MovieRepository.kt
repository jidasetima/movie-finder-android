package com.jogasoft.moviefinder.data

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun observeMovies(): Flow<List<Movie>>
    fun observePagedSearchedMovies(query: String):  Flow<PagingData<Movie>>
    suspend fun synchronizeNowPlayingMovies(): Result<Unit>
    suspend fun synchronizePopularMovies(): Result<Unit>
    suspend fun synchronizeTopRatedMovies(): Result<Unit>
    suspend fun synchronizeUpcomingMovies(): Result<Unit>
    suspend fun getMovieDetailById(movieId: Int): Result<MovieDetail>
}