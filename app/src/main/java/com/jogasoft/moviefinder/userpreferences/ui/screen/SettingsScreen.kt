package com.jogasoft.moviefinder.userpreferences.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.jogasoft.moviefinder.core.ui.component.MovieFinderAppBar
import com.jogasoft.moviefinder.core.ui.theme.MovieFinderTheme
import com.jogasoft.moviefinder.userpreferences.data.model.ThemePreference
import com.jogasoft.moviefinder.userpreferences.ui.SettingsUiState

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    uiState: SettingsUiState,
    updateThemeAction: (ThemePreference) -> Unit,
    navigateBackAction: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = { MovieFinderAppBar(navigateBackAction = navigateBackAction) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Theme",
                style = MaterialTheme.typography.titleLarge
            )
            // Modifier.selectableGroup() is essential to ensure correct accessibility behavior.
            // see https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary#radiobutton
            // for more info
            Column(modifier = Modifier.selectableGroup()) {
                ThemePreference.entries.forEach { theme ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = uiState.theme == theme,
                                onClick = { updateThemeAction(theme) },
                                role = Role.RadioButton
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        RadioButton(
                            selected = uiState.theme == theme,
                            onClick = null
                        )
                        Text(
                            text = theme.name,
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewSettingsScreen() {
    MovieFinderTheme {
        SettingsScreen(
            uiState = SettingsUiState(),
            updateThemeAction = {},
            navigateBackAction = {}
        )
    }
}