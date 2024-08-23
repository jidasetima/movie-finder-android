package com.jogasoft.moviefinder.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jogasoft.moviefinder.ui.component.MovieFinderAppBar
import com.jogasoft.moviefinder.ui.screen.HomeScreen
import com.jogasoft.moviefinder.ui.theme.MovieFinderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieFinderTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { MovieFinderAppBar() }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        val viewModel: HomeViewModel = viewModel()
                        HomeScreen(
                            uiState = viewModel.uiState.collectAsStateWithLifecycle().value
                        )
                    }
                }
            }
        }
    }
}