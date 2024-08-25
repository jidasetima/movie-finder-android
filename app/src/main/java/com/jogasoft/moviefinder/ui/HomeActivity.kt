package com.jogasoft.moviefinder.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.jogasoft.moviefinder.ui.navigation.MovieFinderAppNavigationHost
import com.jogasoft.moviefinder.ui.theme.MovieFinderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieFinderTheme {
                Surface(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    MovieFinderAppNavigationHost()
                }
            }
        }
    }
}