package dev.elenivoreopoulou.dividendtracker.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalDividendDarkTheme = staticCompositionLocalOf { false }
val LocalDividendThemeToggle = staticCompositionLocalOf<() -> Unit> { {} }

@Composable
@Suppress("unused")
fun isDividendInDarkTheme(): Boolean = LocalDividendDarkTheme.current

@Composable
@Suppress("unused")
fun currentDividendThemeToggle(): () -> Unit = LocalDividendThemeToggle.current

private val DarkColors = darkColorScheme(
    primary = PrimaryBlue,
    secondary = SuccessGreen,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = DarkTextPrimary,
    onBackground = DarkTextPrimary,
    onSurface = DarkTextPrimary
)

private val LightColors = lightColorScheme(
    primary = PrimaryBlueDark,
    secondary = SuccessGreen,
    background = LightBackground,
    surface = LightSurface,
    onPrimary = LightTextPrimary,
    onBackground = LightTextPrimary,
    onSurface = LightTextPrimary
)

@Composable
fun DividendTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    onToggleTheme: () -> Unit = {},
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    CompositionLocalProvider(
        LocalDividendDarkTheme provides darkTheme,
        LocalDividendThemeToggle provides onToggleTheme
    ) {
        MaterialTheme(
            colorScheme = colors,
            typography = Typography,
            content = content
        )
    }
}