package com.jogasoft.moviefinder.userpreferences.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jogasoft.moviefinder.userpreferences.data.UserPreferenceRepository
import com.jogasoft.moviefinder.userpreferences.data.model.ThemePreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferenceRepository: UserPreferenceRepository
): ViewModel() {

    val uiState = userPreferenceRepository.observeThemePreference.map { themePreference ->
        SettingsUiState(
            theme = themePreference
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = SettingsUiState()
    )

    fun updateTheme(theme: ThemePreference) {
        viewModelScope.launch {
            userPreferenceRepository.updateThemePreference(theme)
        }
    }
}

data class SettingsUiState(
    val theme: ThemePreference = ThemePreference.SYSTEM
)