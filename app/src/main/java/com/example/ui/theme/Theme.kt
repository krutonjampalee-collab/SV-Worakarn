package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
  darkColorScheme(
    primary = CyanAccent,
    onPrimary = SlateDark,
    primaryContainer = RoyalBlue,
    onPrimaryContainer = Color.White,
    secondary = GoldAccent,
    onSecondary = SlateDark,
    background = SlateDark,
    onBackground = TextLight,
    surface = SlateCard,
    onSurface = TextLight,
    surfaceVariant = SlateBorder,
    onSurfaceVariant = TextLight,
  )

private val LightColorScheme =
  lightColorScheme(
    primary = RoyalBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE0ECFB),
    onPrimaryContainer = NavyPrimary,
    secondary = GoldDark,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFEF3C7),
    onSecondaryContainer = Color(0xFF78350F),
    background = SurfaceLight,
    onBackground = TextDark,
    surface = SurfaceCardLight,
    onSurface = TextDark,
    surfaceVariant = Color(0xFFE2E8F0),
    onSurfaceVariant = Color(0xFF334155),
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Keep consistent professional government branding
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content,
  )
}
