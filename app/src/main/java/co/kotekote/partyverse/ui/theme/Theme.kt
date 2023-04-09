package co.kotekote.partyverse.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,
)

val LocalDarkThemeState = compositionLocalOf { false }

@Composable
fun PartyverseTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    CompositionLocalProvider(
        LocalDarkThemeState provides darkTheme
    ) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.background),
                content = content
            )
        }
    }
}