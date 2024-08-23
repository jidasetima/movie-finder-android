package com.jogasoft.moviefinder.ui.component

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.jogasoft.moviefinder.R
import org.junit.Rule
import org.junit.Test

class MovieFinderAppBarTest {
    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun verify_app_bar_title() {
        rule.setContent { MovieFinderAppBar() }
        rule.onNodeWithText(rule.activity.getString(R.string.app_name)).assertIsDisplayed()
    }
}