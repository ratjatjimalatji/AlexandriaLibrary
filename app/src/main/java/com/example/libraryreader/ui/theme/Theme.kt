package com.example.libraryreader.ui.theme

import android.app.Activity
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
    primary = OppoLightGreen,     // Oppo Green but slightly more vibrant for dark contrast
    secondary = AccentPurple,     // Your requested purple accent
    background = DarkGreyBackground,
    surface = DarkGreySurface,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = SoftWhite,     // Subtle white text
    onSurface = SoftWhite
)

private val LightColorScheme = lightColorScheme(
    primary = OppoGreen,          // Standard Oppo Brand Green
    secondary = Color(0xFF6200EE), // Standard Purple
    background = Color(0xFFF9F9F9),
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F)
)

@Composable
fun LibraryReaderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Set this to false to ensure your Oppo branding stays consistent
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