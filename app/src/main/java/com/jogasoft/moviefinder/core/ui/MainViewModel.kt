package com.jogasoft.moviefinder.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jogasoft.moviefinder.userpreferences.data.UserPreferenceRepository
import com.jogasoft.moviefinder.userpreferences.data.model.ThemePreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    userPreferenceRepository: UserPreferenceRepository
): ViewModel() {
    val uiState = userPreferenceRepository.observeThemePreference.mapLatest { theme ->
        MainUiState(theme)
    }.stateIn(
        started = SharingStarted.WhileSubscribed(5000L),
        scope = viewModelScope,
        initialValue = MainUiState()
    )
}

data class MainUiState(
    val theme: ThemePreference = ThemePreference.SYSTEM
)