package com.jogasoft.moviefinder.userpreferences.data

import com.jogasoft.moviefinder.userpreferences.data.model.ThemePreference
import com.jogasoft.moviefinder.userpreferences.data.source.local.FakeUserPreferenceLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class DefaultUserPreferenceRepositoryTest {

    private val userPreferenceRepository = FakeUserPreferenceLocalDataSource()
    private val defaultUserPreferenceRepository = DefaultUserPreferenceRepository(userPreferenceRepository)

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        runTest {
            userPreferenceRepository.updateThemePreference(ThemePreference.SYSTEM)
        }
        Dispatchers.resetMain()
    }

    @Test
    fun observeThemePreference_returns_expected_value() = runTest {
        assertEquals(ThemePreference.SYSTEM, defaultUserPreferenceRepository.observeThemePreference.first())
    }

    @Test
    fun updateThemePreference_properly_updates_localDataSource() = runTest {
        ThemePreference.entries.forEach { themePreference ->
            userPreferenceRepository.updateThemePreference(themePreference)
            assertEquals(themePreference, defaultUserPreferenceRepository.observeThemePreference.first())
        }
    }
}