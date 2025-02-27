package com.ajeeb.spendie.common.core

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Green,
    onPrimary = White,
    background = Ink,
    onBackground = White,
    surface = Slate,
    secondary = Silver,
    onError = Flame
)

private val LightColorScheme = lightColorScheme(
    primary = Green,
    onPrimary = White,
    background = White,
    onBackground = Coal,
    surface = Dove,
    secondary = Silver,
    onError = Flame
)

@Composable
fun SpendieTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme, typography = bumbleCarTypography(), content = content
    )
}