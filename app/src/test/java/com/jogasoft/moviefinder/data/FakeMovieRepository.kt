package com.jogasoft.moviefinder.data

class FakeMovieRepository(
    private val movies: List<Movie>,
    private val movieDetail: MovieDetail
): MovieRepository {
    override suspend fun getNowPlayingMovies(): Result<List<Movie>> = Result.success(movies)
    override suspend fun getPopularMovies(): Result<List<Movie>> = Result.success(movies)
    override suspend fun getTopRatedMovies(): Result<List<Movie>> = Result.success(movies)
    override suspend fun getUpcomingMovies(): Result<List<Movie>> = Result.success(movies)
    override suspend fun getMovieDetailById(movieId: Int): Result<MovieDetail>  = Result.success(movieDetail)
}