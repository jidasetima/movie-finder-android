package com.jogasoft.moviefinder.movie.ui.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
import com.jogasoft.moviefinder.R
import com.jogasoft.moviefinder.movie.ui.viewModel.MovieCategoryListUiState
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieCategoryListScreenTest {
    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp() {
        rule.setContent { MovieCategoryListScreen(
            uiState =  MovieCategoryListUiState(),
            navigateToMovieDetail = {},
            navigateToSearch = {},
            navigateToSettings = {}
        ) }
    }

    @Test
    fun expected_movie_category_titles_appear() {
        val nowPlayingText = rule.activity.getString(R.string.now_playing)
        rule.onNodeWithTag(MovieCategoryListLazyColumnTestTag).performScrollToNode(hasText(nowPlayingText))
        rule.onNodeWithText(nowPlayingText).assertIsDisplayed()

        val popularText = rule.activity.getString(R.string.popular)
        rule.onNodeWithTag(MovieCategoryListLazyColumnTestTag).performScrollToNode(hasText(popularText))
        rule.onNodeWithText(popularText).assertIsDisplayed()

        val topRatedText = rule.activity.getString(R.string.top_rated)
        rule.onNodeWithTag(MovieCategoryListLazyColumnTestTag).performScrollToNode(hasText(topRatedText))
        rule.onNodeWithText(topRatedText).assertIsDisplayed()

        val upcomingText = rule.activity.getString(R.string.upcoming)
        rule.onNodeWithTag(MovieCategoryListLazyColumnTestTag).performScrollToNode(hasText(upcomingText))
        rule.onNodeWithText(upcomingText).assertIsDisplayed()
    }

    @Test
    fun confirm_search_icon_isDisplayed() {
        rule.onNodeWithContentDescription(rule.activity.getString(R.string.search_icon_button)).assertIsDisplayed()
    }
}