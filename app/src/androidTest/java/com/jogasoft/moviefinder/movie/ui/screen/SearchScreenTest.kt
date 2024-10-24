package com.jogasoft.moviefinder.movie.ui.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.jogasoft.moviefinder.R
import com.jogasoft.moviefinder.movie.data.Movie
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {
    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp() {
        rule.setContent { SearchScreen(
            moviePagerItems = flowOf(PagingData.from(listOf<Movie>())).collectAsLazyPagingItems(),
            searchText = "",
            navigateToMovieDetailAction = {},
            navigateBackAction = {},
            updateSearchTextAction = {}
        ) }
    }

    @Test
    fun verify_default_elements_displayed() {
        rule.onNodeWithContentDescription(rule.activity.getString(R.string.back_navigation_arrow_button)).assertIsDisplayed()
        rule.onNodeWithContentDescription(rule.activity.getString(R.string.clear_search_button)).assertIsNotDisplayed()
        rule.onNodeWithText(rule.activity.getString(R.string.search_movies)).assertIsDisplayed()
    }
}