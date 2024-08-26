package com.jogasoft.moviefinder.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.jogasoft.moviefinder.R
import com.jogasoft.moviefinder.ui.theme.MovieFinderTheme

//Test tags
const val AppBarBackButtonTestTag = "AppBarBackButtonTestTag"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieFinderAppBar(
    modifier: Modifier = Modifier,
    navigateBackAction: (() -> Unit)? = null
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleLarge
            )
                },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        navigationIcon = {
            navigateBackAction?.let {
                IconButton(
                    modifier = Modifier.testTag(AppBarBackButtonTestTag),
                    onClick = { navigateBackAction() }
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Navigation Back Arrow"
                    )
                }
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun PreviewMovieFinderAppBar() {
    MovieFinderTheme {
        MovieFinderAppBar(
            navigateBackAction = {}
        )
    }
}