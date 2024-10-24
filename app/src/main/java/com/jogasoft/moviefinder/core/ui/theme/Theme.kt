package com.jogasoft.moviefinder.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Blue80,
    onPrimary = Blue20,
    primaryContainer = Blue30,
    onPrimaryContainer = Blue90,
    secondary = DarkBlue80,
    onSecondary = DarkBlue20,
    secondaryContainer = DarkBlue30,
    onSecondaryContainer = DarkBlue90,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = DarkBlueGray10,
    onBackground = DarkBlueGray90,
    surface = DarkBlueGray10,
    onSurface = DarkBlueGray90,
    surfaceVariant = BlueGray30,
    onSurfaceVariant = BlueGray80,
    inverseSurface = DarkBlueGray90,
    inverseOnSurface = DarkBlueGray10,
    outline = BlueGray60
)

private val LightColorScheme = lightColorScheme(
    primary = Blue40,
    onPrimary = Color.White,
    primaryContainer = Blue80,
    onPrimaryContainer = Blue10,
    secondary = DarkBlue40,
    onSecondary = Color.White,
    secondaryContainer = Blue80,
    onSecondaryContainer = Blue10,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = DarkBlueGray99,
    onBackground = DarkBlueGray10,
    surface = DarkBlueGray95,
    onSurface = DarkBlueGray10,
    surfaceVariant = BlueGray90,
    onSurfaceVariant = BlueGray30,
    inverseSurface = DarkBlueGray30,
    inverseOnSurface = DarkBlueGray95,
    outline = BlueGray60
)

@Composable
fun MovieFinderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}