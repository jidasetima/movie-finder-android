package com.jogasoft.moviefinder.movie.data.source.network

import com.jogasoft.moviefinder.movie.data.source.network.model.movie.NetworkMovie
import com.jogasoft.moviefinder.movie.data.source.network.model.movie.NetworkMoviePage
import com.jogasoft.moviefinder.movie.data.source.network.model.movieDetail.NetworkMovieDetail

interface MovieNetworkDataSource {
    suspend fun getNowPlayingMovies(): Result<List<NetworkMovie>>
    suspend fun getPopularMovies(): Result<List<NetworkMovie>>
    suspend fun getTopRatedMovies(): Result<List<NetworkMovie>>
    suspend fun getUpcomingMovies(): Result<List<NetworkMovie>>
    suspend fun paginateSearchedMovies(page: Int, query: String): Result<NetworkMoviePage>
    suspend fun getMovieDetailById(movieId: Int): Result<NetworkMovieDetail>
}