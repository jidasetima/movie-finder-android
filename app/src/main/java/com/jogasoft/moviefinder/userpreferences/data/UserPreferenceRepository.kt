package com.jogasoft.moviefinder.userpreferences.data

import com.jogasoft.moviefinder.userpreferences.data.model.ThemePreference
import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository {
    val observeThemePreference: Flow<ThemePreference>
    suspend fun updateThemePreference(themePreference: ThemePreference)
}