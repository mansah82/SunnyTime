package com.example.sunnytime.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = TreeBrown,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = SunnyYellow,
    onBackground = SunnyYellowTransparent
)

private val LightColorPalette = lightColors(
    primary = TreeBrown,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = SunnyYellow,
    onBackground = SunnyYellowTransparent

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun SunnyTimeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}