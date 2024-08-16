package com.jogasoft.moviefinder.di

import com.jogasoft.moviefinder.data.DefaultMovieRepository
import com.jogasoft.moviefinder.data.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindMovieRepository(movieRepository: DefaultMovieRepository): MovieRepository
}