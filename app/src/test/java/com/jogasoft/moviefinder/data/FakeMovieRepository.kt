package com.jogasoft.moviefinder.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeMovieRepository(
    private val movieDetail: MovieDetail,
    private val movieList: List<Movie> = listOf()
): MovieRepository {
    private val _movies = MutableSharedFlow<List<Movie>>()
    suspend fun emit(movies: List<Movie>) = _movies.emit(movies)
    override fun observeMovies(): Flow<List<Movie>> = _movies

    override suspend fun synchronizeNowPlayingMovies(): Result<Unit> = Result.success(Unit)
    override suspend fun synchronizePopularMovies(): Result<Unit> = Result.success(Unit)
    override suspend fun synchronizeTopRatedMovies(): Result<Unit> = Result.success(Unit)
    override suspend fun synchronizeUpcomingMovies(): Result<Unit> = Result.success(Unit)
    override suspend fun getMoviesBySearchQuery(query: String): Result<List<Movie>> {
        val filteredMovies = movieList.filter { it.title.contains(query) }
        return Result.success(filteredMovies)
    }

    override suspend fun getMovieDetailById(movieId: Int): Result<MovieDetail>  = Result.success(movieDetail)
}