package com.jogasoft.moviefinder.data.source.local.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.jogasoft.moviefinder.data.source.local.LocalMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * from movie")
    fun observeMovies(): Flow<List<LocalMovie>>

    @Upsert
    suspend fun upsertAll(vararg movies: LocalMovie)
}