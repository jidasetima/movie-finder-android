package com.jogasoft.moviefinder.ui.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.jogasoft.moviefinder.data.MovieDetail
import com.jogasoft.moviefinder.ui.viewModel.MovieDetailUiState
import org.junit.Rule
import org.junit.Test

class MovieDetailScreenTest {
    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun verify_movieDetail_withLoadingState_displays_loadingElement() {
        rule.setContent {
            MovieDetailScreen(
                uiState = MovieDetailUiState.Loading,
                navigateBackAction = {}
            )
        }

        rule.onNodeWithTag(MovieDetailLoadingTestTag).assertIsDisplayed()
    }

    @Test
    fun verify_movieDetail_withSuccessState_display_movieDetailData() {
        val movieDetail = MovieDetail(
            id = 1,
            backdropPath = "Fake Path",
            budget = 1,
            genres = listOf("Action", "Comedy", "Romance"),
            overview = "Fake Overview",
            popularity = 1.0,
            posterPath = "Fake Path",
            releaseDate = "2012-02-10",
            runtime = 100,
            tagline = "Fake Tagline",
            title = "Fake Title"
        )
        rule.setContent {
            MovieDetailScreen(
                uiState = MovieDetailUiState.Success(movieDetail = movieDetail),
                navigateBackAction = {}
            )
        }

        rule.onNodeWithTag(MovieDetailLoadingTestTag).assertIsNotDisplayed()
        rule.onNodeWithText(movieDetail.title).assertIsDisplayed()
        rule.onNodeWithText(movieDetail.overview).assertIsDisplayed()
        rule.onNodeWithText(movieDetail.releaseDate).assertIsDisplayed()
        movieDetail.genres.forEach { genre ->
            rule.onNodeWithText(genre).assertIsDisplayed()
        }
        rule.onNodeWithText("1h 40m").assertIsDisplayed()
    }
}