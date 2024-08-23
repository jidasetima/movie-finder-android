package com.jogasoft.moviefinder.ui.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToNode
import com.jogasoft.moviefinder.R
import com.jogasoft.moviefinder.ui.HomeUiState
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun expected_movie_category_titles_appear() {
        rule.setContent { HomeScreen(uiState =  HomeUiState()) }

        val nowPlayingText = rule.activity.getString(R.string.now_playing)
        rule.onNodeWithTag(HomeLazyColumnTestTag).performScrollToNode(hasText(nowPlayingText))
        rule.onNodeWithText(nowPlayingText).assertIsDisplayed()

        val popularText = rule.activity.getString(R.string.popular)
        rule.onNodeWithTag(HomeLazyColumnTestTag).performScrollToNode(hasText(popularText))
        rule.onNodeWithText(popularText).assertIsDisplayed()

        val topRatedText = rule.activity.getString(R.string.top_rated)
        rule.onNodeWithTag(HomeLazyColumnTestTag).performScrollToNode(hasText(topRatedText))
        rule.onNodeWithText(topRatedText).assertIsDisplayed()

        val upcomingText = rule.activity.getString(R.string.upcoming)
        rule.onNodeWithTag(HomeLazyColumnTestTag).performScrollToNode(hasText(upcomingText))
        rule.onNodeWithText(upcomingText).assertIsDisplayed()
    }
}