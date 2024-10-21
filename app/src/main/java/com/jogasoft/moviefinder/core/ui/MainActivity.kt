package com.jogasoft.moviefinder.core.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.jogasoft.moviefinder.core.ui.navigation.MovieFinderAppNavigationHost
import com.jogasoft.moviefinder.core.ui.theme.MovieFinderTheme
import com.jogasoft.moviefinder.userpreferences.data.model.ThemePreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val theme =  viewModel.uiState.collectAsStateWithLifecycle().value.theme
            val useDarkTheme = when(theme) {
                ThemePreference.LIGHT -> false
                ThemePreference.DARK -> true
                ThemePreference.SYSTEM -> isSystemInDarkTheme()
            }
            MovieFinderTheme(
                darkTheme = useDarkTheme
            ) {
                Surface(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    val navController = rememberNavController()
                    MovieFinderAppNavigationHost(navController)
                }
            }
        }
    }
}