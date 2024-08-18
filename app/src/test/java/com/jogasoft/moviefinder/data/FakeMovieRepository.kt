package com.jogasoft.moviefinder.data

class FakeMovieRepository(private val movies: List<Movie>): MovieRepository {
    override suspend fun getNowPlayingMovies(): Result<List<Movie>> {
        return Result.success(movies)
    }
}