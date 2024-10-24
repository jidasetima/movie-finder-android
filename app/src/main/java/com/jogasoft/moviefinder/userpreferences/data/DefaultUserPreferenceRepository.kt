package com.jogasoft.moviefinder.userpreferences.data

import com.jogasoft.moviefinder.userpreferences.data.model.ThemePreference
import com.jogasoft.moviefinder.userpreferences.data.source.local.UserPreferencesLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultUserPreferenceRepository @Inject constructor(
    private val userPreferencesLocalDataSource: UserPreferencesLocalDataSource
): UserPreferenceRepository {
    override val observeThemePreference: Flow<ThemePreference> =
        userPreferencesLocalDataSource.observeThemePreference

    override suspend fun updateThemePreference(themePreference: ThemePreference) {
        userPreferencesLocalDataSource.updateThemePreference(themePreference)
    }
}