package com.jogasoft.moviefinder.di

import com.jogasoft.moviefinder.BuildConfig
import com.jogasoft.moviefinder.movie.data.source.network.DefaultMovieNetworkDataSource
import com.jogasoft.moviefinder.movie.data.source.network.MovieApi
import com.jogasoft.moviefinder.movie.data.source.network.MovieNetworkDataSource
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
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
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val originalRequest = chain.request()

                    val authorizedRequest = originalRequest.newBuilder()
                        .addHeader("Authorization", "Bearer ${BuildConfig.TMDB_API_KEY}")
                        .build()

                     chain.proceed(authorizedRequest)
                }
                .build()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_TMDB_URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
        }

        @Provides
        fun providesMovieApi(retrofit: Retrofit): MovieApi {
            return retrofit.create(MovieApi::class.java)
        }
    }
}