package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val NeuroDarkColorScheme =
  darkColorScheme(
    primary = NeuroGreen,
    onPrimary = NeuroBg,
    secondary = NeuroCyan,
    onSecondary = NeuroBg,
    tertiary = NeuroPurple,
    background = NeuroBg,
    onBackground = NeuroText,
    surface = NeuroCard,
    onSurface = NeuroText,
    outline = NeuroBorder
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true,
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = NeuroDarkColorScheme,
    typography = Typography,
    content = content
  )
}

