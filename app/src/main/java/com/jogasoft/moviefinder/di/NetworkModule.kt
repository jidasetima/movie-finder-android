package com.jogasoft.moviefinder.di

import com.jogasoft.moviefinder.BuildConfig
import com.jogasoft.moviefinder.data.source.network.DefaultMovieNetworkDataSource
import com.jogasoft.moviefinder.data.source.network.MovieApi
import com.jogasoft.moviefinder.data.source.network.MovieNetworkDataSource
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    @Binds
    abstract fun bindsMovieNetworkDataSource(movieNetworkDataSource: DefaultMovieNetworkDataSource): MovieNetworkDataSource

    companion object {
        @Singleton
        @Provides
        fun providesRetrofit(): Retrofit {
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_TMDB_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
        }

        @Provides
        fun providesMovieApi(retrofit: Retrofit): MovieApi {
            return retrofit.create(MovieApi::class.java)
        }
    }
}