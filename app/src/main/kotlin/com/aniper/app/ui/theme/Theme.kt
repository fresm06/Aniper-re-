package com.aniper.app.ui.theme

import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = AccentPurple,
    secondary = AccentMint,
    tertiary = AccentPink,
    error = Error,
    background = BackgroundDark,
    surface = BackgroundSecondary,
    onPrimary = BackgroundDark,
    onSecondary = BackgroundDark,
    onTertiary = BackgroundDark,
    onError = BackgroundDark,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    outline = TextSecondary
)

@Composable
fun AnIperTheme(
    dynamicColor: Boolean = false,
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val actualDarkTheme = darkTheme
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (actualDarkTheme) dynamicDarkColorScheme(context) else DarkColorScheme
        }
        else -> DarkColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AnIperTypography,
        content = content
    )
}
