package com.jogasoft.moviefinder.ui.navigation

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.jogasoft.moviefinder.ui.HomeActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<HomeActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            MovieFinderAppNavigationHost(navController)
        }
        composeRule.onNodeWithTag(NavHostTestTag).assertExists()
    }

    @Test
    fun navHost_verifyStartDestination_isHomeScreen() {
        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(HOME_SCREEN_ROUTE, route)
    }

    companion object {
        const val HOME_SCREEN_ROUTE = "com.jogasoft.moviefinder.ui.navigation.HomeScreen"
    }
}