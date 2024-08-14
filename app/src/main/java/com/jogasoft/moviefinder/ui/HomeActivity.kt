package com.jogasoft.moviefinder.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jogasoft.moviefinder.ui.screen.HomeScreen
import com.jogasoft.moviefinder.ui.theme.MovieFinderTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieFinderTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel: HomeViewModel = viewModel()
                    HomeScreen(
                        modifier = Modifier.padding(innerPadding),
                        uiState = viewModel.uiState.collectAsStateWithLifecycle().value
                    )
                }
            }
        }
    }
}