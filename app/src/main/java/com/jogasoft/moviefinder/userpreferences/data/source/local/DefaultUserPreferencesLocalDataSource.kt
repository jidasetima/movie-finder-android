package com.jogasoft.moviefinder.userpreferences.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.jogasoft.moviefinder.userpreferences.data.model.ThemePreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultUserPreferencesLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserPreferencesLocalDataSource {
    override val observeThemePreference: Flow<ThemePreference> = dataStore.data.map { preferences ->
        val themeName = preferences[THEME_PREFERENCE_KEY] ?: DEFAULT_THEME_PREFERENCE.name
        ThemePreference.valueOf(themeName)
    }

    override suspend fun updateThemePreference(themePreference: ThemePreference) {
        dataStore.edit { preferences ->
            preferences[THEME_PREFERENCE_KEY] = themePreference.name
        }
    }

    companion object {
        private val DEFAULT_THEME_PREFERENCE = ThemePreference.SYSTEM
        private val THEME_PREFERENCE_KEY = stringPreferencesKey("theme_preference_key")
    }
}