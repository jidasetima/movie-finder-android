package com.jogasoft.moviefinder.data.source.local

import com.jogasoft.moviefinder.data.source.local.database.MovieDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeMovieDao: MovieDao {
    private val localMovies = mutableListOf<LocalMovie>()
    override fun observeMovies(): Flow<List<LocalMovie>> = flowOf(localMovies)

    override suspend fun upsertAll(vararg movies: LocalMovie) {
        movies.forEach { movie ->
            val index = localMovies.indexOfFirst { it.id == movie.id && it.category == movie.category }

            if (index == -1) {
                localMovies.add(movie)
            } else {
                localMovies[index] = movie
            }
        }
    }
}