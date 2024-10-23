package com.jogasoft.moviefinder.userpreferences.data.source.local

import com.jogasoft.moviefinder.userpreferences.data.model.ThemePreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FakeUserPreferenceLocalDataSource: UserPreferencesLocalDataSource {
    private var themePreferenceFlow = MutableStateFlow(ThemePreference.SYSTEM)
    override val observeThemePreference: Flow<ThemePreference>
        get() = themePreferenceFlow.asStateFlow()

    override suspend fun updateThemePreference(themePreference: ThemePreference) {
        this.themePreferenceFlow.update { themePreference }
    }
}