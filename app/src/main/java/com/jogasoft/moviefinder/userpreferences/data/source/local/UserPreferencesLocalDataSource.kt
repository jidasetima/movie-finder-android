package com.jogasoft.moviefinder.userpreferences.data.source.local

import com.jogasoft.moviefinder.userpreferences.data.model.ThemePreference
import kotlinx.coroutines.flow.Flow

interface UserPreferencesLocalDataSource {
    val observeThemePreference: Flow<ThemePreference>
    suspend fun updateThemePreference(themePreference: ThemePreference)
}