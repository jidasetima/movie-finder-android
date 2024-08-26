package com.jogasoft.moviefinder.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val Typography = Typography(
    headlineLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 38.sp,
        letterSpacing = 0.1.sp,
    ),

    headlineMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        letterSpacing = 0.1.sp,
    ),

    headlineSmall = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.1.sp,
    ),

    titleLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp,
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
)

@Preview
@Composable
private fun PreviewTypography() {
    MovieFinderTheme {
        Column(
            Modifier
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Display Large",
                style = MaterialTheme.typography.displayLarge
            )
            Text(
                text = "Display Medium",
                style = MaterialTheme.typography.displayMedium
            )
            Text(
                text = "Display Small",
                style = MaterialTheme.typography.displaySmall
            )
            Text(
                text = "Headline Large",
                style = MaterialTheme.typography.headlineLarge
            )

            Text(
                text = "Headline Medium",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Headline Small",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Title Large",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Title Medium",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Title Small",
                style = MaterialTheme.typography.titleSmall
            )

            Text(
                text = "Label Large",
                style = MaterialTheme.typography.labelLarge
            )

            Text(
                text = "Label Medium",
                style = MaterialTheme.typography.labelMedium
            )

            Text(
                text = "Label Small",
                style = MaterialTheme.typography.labelSmall
            )

            Text(
                text = "This is a Large Body",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "This is a Medium Body",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "This is a Small Body",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}