package com.example.univoice.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    secondary = SecondaryLavender,
    tertiary = WarmAccent,

    background = AppBackground,
    surface = CardWhite,

    onPrimary = CardWhite,
    onSecondary = CardWhite,
    onTertiary = DarkText,

    onBackground = DarkText,
    onSurface = DarkText
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlue,
    secondary = SecondaryLavender,
    tertiary = WarmAccent,

    background = DarkText,
    surface = Color(0xFF2B2F36),

    onPrimary = CardWhite,
    onSecondary = CardWhite,
    onTertiary = DarkText,

    onBackground = CardWhite,
    onSurface = CardWhite
)

@Composable
fun UNIVOICETheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}