package com.maku.pombe.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = PombePrimaryDark,
    primaryVariant = PombePrimary,
    onPrimary = Color.Black,
    secondary = PombeSecondary,
    onSecondary = Color.White,
    error = Red800,
    background = Color.Black,
    surface = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorPalette = lightColors(
    primary = PombePrimaryDark,
    primaryVariant = PombePrimary,
    onPrimary = Color.White,
    secondary = PombeSecondary,
    secondaryVariant = PombeSecondaryDark,
    onSecondary = Color.Black,
    error = Red800,
    background = Color.White,
    surface = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun PombeTheme(darkTheme: Boolean = PombeThemeSettings.isInDarkTheme.value, content: @Composable() () -> Unit) {
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

object PombeThemeSettings {
    var isInDarkTheme: MutableState<Boolean> = mutableStateOf(false)
}