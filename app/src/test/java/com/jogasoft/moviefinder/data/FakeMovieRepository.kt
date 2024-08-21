package com.jogasoft.moviefinder.data

class FakeMovieRepository(private val movies: List<Movie>): MovieRepository {
    override suspend fun getNowPlayingMovies(): Result<List<Movie>> = Result.success(movies)
    override suspend fun getPopularMovies(): Result<List<Movie>> = Result.success(movies)
    override suspend fun getTopRatedMovies(): Result<List<Movie>> = Result.success(movies)
    override suspend fun getUpcomingMovies(): Result<List<Movie>> = Result.success(movies)
}