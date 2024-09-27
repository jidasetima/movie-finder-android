package com.jogasoft.moviefinder.data.source.local.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.jogasoft.moviefinder.data.MovieCategory
import com.jogasoft.moviefinder.data.source.local.LocalMovie
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MovieDao {
    @Query("SELECT * from movie")
    abstract fun observeMovies(): Flow<List<LocalMovie>>

    @Upsert
    abstract suspend fun upsertAll(vararg movies: LocalMovie)

    @Query("DELETE FROM movie WHERE category = :category")
    abstract fun deleteMovieByCategory(category: MovieCategory)

    @Transaction
    open suspend fun clearAndInsertMoviesByCategory(vararg movies: LocalMovie, category: MovieCategory) {
        deleteMovieByCategory(category)
        upsertAll(*movies)
    }
}