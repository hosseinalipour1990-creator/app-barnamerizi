package com.hosseinalipour.barnamerizi.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    primary = Mint,
    onPrimary = Ink,
    primaryContainer = MintContainer,
    onPrimaryContainer = TextPrimary,
    secondary = Sky,
    onSecondary = Ink,
    background = Ink,
    onBackground = TextPrimary,
    surface = DeepSurface,
    onSurface = TextPrimary,
    surfaceVariant = ElevatedSurface,
    onSurfaceVariant = TextSecondary,
    outline = SoftBorder,
    error = Rose,
    onError = Ink,
    scrim = Color.Black
)

@Composable
fun BarnameriziTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColors,
        typography = AppTypography,
        content = content
    )
}
