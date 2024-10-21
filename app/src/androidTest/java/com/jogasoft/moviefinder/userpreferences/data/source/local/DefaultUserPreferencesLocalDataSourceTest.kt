package com.jogasoft.moviefinder.userpreferences.data.source.local

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import com.jogasoft.moviefinder.userpreferences.data.model.ThemePreference
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotSame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.createTestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class DefaultUserPreferencesLocalDataSourceTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val testDispatcher = StandardTestDispatcher()
    private val testCoroutineScope = CoroutineScope(testDispatcher)
    private val dataStore = PreferenceDataStoreFactory.create(
        scope = testCoroutineScope,
        produceFile = {
            context.preferencesDataStoreFile("test_user_preferences_dataStore")
        }
    )
    private lateinit var defaultUserPreferencesLocalDataSource: DefaultUserPreferencesLocalDataSource

    @Before
    fun setUp() {
        defaultUserPreferencesLocalDataSource = DefaultUserPreferencesLocalDataSource(dataStore)
    }

    @After
    fun tearDown() {
        testCoroutineScope.launch { dataStore.edit { it.clear() } }
        testCoroutineScope.cancel()
    }

    @Test
    fun confirm_default_theme_is_system()  {
        testCoroutineScope.launch {
            val theme = defaultUserPreferencesLocalDataSource.observeThemePreference.first()
            assertEquals(ThemePreference.SYSTEM, theme)
        }
    }

    @Test
    fun updateThemePreference_properly_updates_dataStore() {
        testCoroutineScope.launch {
            val initialTheme = defaultUserPreferencesLocalDataSource.observeThemePreference.first()
            assertNotSame(ThemePreference.DARK, initialTheme)

            defaultUserPreferencesLocalDataSource.updateThemePreference(ThemePreference.DARK)

            val updatedTheme = defaultUserPreferencesLocalDataSource.observeThemePreference.first()

            assertEquals(ThemePreference.DARK, updatedTheme)
        }
    }
}