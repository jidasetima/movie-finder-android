package com.jogasoft.moviefinder.data.source.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jogasoft.moviefinder.data.source.network.model.movie.NetworkMovie
import kotlin.coroutines.cancellation.CancellationException
import kotlin.random.Random

class SearchMoviePagingSource(
    private val movieNetworkDataSource: MovieNetworkDataSource,
    private val query: String
): PagingSource<Int, NetworkMovie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NetworkMovie> {
        try {
            val nextPageNumber = params.key ?: 1
            movieNetworkDataSource.paginateSearchedMovies(nextPageNumber, query).fold(
                onSuccess = { networkMoviePage ->
                    val networkMovies = networkMoviePage.results
                    return LoadResult.Page(
                        data = networkMovies,
                        prevKey = null,
                        nextKey = if (networkMovies.isNotEmpty()) networkMoviePage.page + 1 else null
                    )
                },
                onFailure = { e ->
                    return LoadResult.Error(e)
                }
            )
            //todo revist cancellation exception here
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NetworkMovie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}