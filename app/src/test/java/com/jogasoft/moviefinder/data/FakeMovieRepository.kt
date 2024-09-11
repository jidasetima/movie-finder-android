package com.jogasoft.moviefinder.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.testing.asPagingSourceFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeMovieRepository(
    private val movieDetail: MovieDetail,
    private val movieList: List<Movie> = listOf()
): MovieRepository {
    private val _movies = MutableSharedFlow<List<Movie>>()
    suspend fun emit(movies: List<Movie>) = _movies.emit(movies)
    override fun observeMovies(): Flow<List<Movie>> = _movies
    override fun observePagedSearchedMovies(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 3
            ),
            initialKey = null,
            pagingSourceFactory =  movieList.asPagingSourceFactory()
        ).flow
    }

    override suspend fun synchronizeNowPlayingMovies(): Result<Unit> = Result.success(Unit)
    override suspend fun synchronizePopularMovies(): Result<Unit> = Result.success(Unit)
    override suspend fun synchronizeTopRatedMovies(): Result<Unit> = Result.success(Unit)
    override suspend fun synchronizeUpcomingMovies(): Result<Unit> = Result.success(Unit)
    override suspend fun getMovieDetailById(movieId: Int): Result<MovieDetail>  = Result.success(movieDetail)
}