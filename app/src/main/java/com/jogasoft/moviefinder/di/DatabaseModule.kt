package com.jogasoft.moviefinder.di

import android.content.Context
import androidx.room.Room
import com.jogasoft.moviefinder.data.source.local.database.MovieDao
import com.jogasoft.moviefinder.data.source.local.database.MovieFinderDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext applicationContext: Context): MovieFinderDatabase {
        return Room.databaseBuilder(
            applicationContext,
            MovieFinderDatabase::class.java,
            "movie-finder-database"
        ).build()
    }

    @Provides
    fun providesMovieDao(movieFinderDatabase: MovieFinderDatabase): MovieDao {
        return movieFinderDatabase.MovieDao()
    }
}