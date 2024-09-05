package com.jogasoft.moviefinder.ui.component

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.jogasoft.moviefinder.R
import com.jogasoft.moviefinder.ui.theme.DarkBlueGray90
import com.jogasoft.moviefinder.ui.theme.MovieFinderTheme


@Composable
fun MovieItemImageLoader(
    modifier: Modifier = Modifier,
    imageUrl: String,
    movieTitle: String,
    navigateToMovieDetailAction: () -> Unit
) {
    SubcomposeAsyncImage(
        model = imageUrl,
        loading = {
            MovieItemAnimatedLoadingPlaceholder()
        },
        error = {
            MovieItemNoImagePlaceholder(title = movieTitle)
        },
        contentDescription = stringResource(
            R.string.movie_title_content_description,
            movieTitle
        ),
        modifier = modifier
            .aspectRatio(2f / 3f)
            .clickable { navigateToMovieDetailAction() }
    )
}

@Composable
fun MovieItemAnimatedLoadingPlaceholder(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "AnimatedPlaceholderMovie")
    val color by infiniteTransition.animateColor(
        initialValue = Color.DarkGray,
        targetValue = Color.Gray,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ), label = "AnimatedPlaceholderMovie Color"
    )
    Box(
        modifier = modifier
            .background(color)
    )
}

@Composable
fun MovieItemNoImagePlaceholder(
    modifier: Modifier = Modifier,
    title: String
) {
    Column(
        modifier = modifier
            .background(Color.DarkGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = DarkBlueGray90,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        Text(
            text = "(No Image)",
            color = DarkBlueGray90,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun PreviewMovieItemAnimatedLoadingPlaceholder() {
    MovieFinderTheme {
        MovieItemAnimatedLoadingPlaceholder(
            modifier = Modifier
                .aspectRatio(2f / 3f)
                .size(400.dp)
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewMovieItemNoImagePlaceholder() {
    MovieFinderTheme {
        MovieItemNoImagePlaceholder(
            modifier = Modifier
                .aspectRatio(2f / 3f)
                .size(400.dp),
            title = "Movie Title"
        )
    }
}