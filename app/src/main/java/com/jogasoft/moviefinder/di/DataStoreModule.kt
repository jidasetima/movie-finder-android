package com.jogasoft.moviefinder.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.jogasoft.moviefinder.userpreferences.data.source.local.DefaultUserPreferencesLocalDataSource
import com.jogasoft.moviefinder.userpreferences.data.source.local.UserPreferencesLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {
    @Singleton
    @Binds
    abstract fun bindUserPreferencesLocalDataSource(
        defaultUserPreferencesLocalDataSource: DefaultUserPreferencesLocalDataSource
    ): UserPreferencesLocalDataSource

    companion object {
        @Singleton
        @Provides
        fun provideUserPreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create(
                corruptionHandler = ReplaceFileCorruptionHandler{ emptyPreferences() },
                scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
                produceFile = {
                    context.preferencesDataStoreFile("user_preferences_dataStore")
                }
            )
        }
    }
}

