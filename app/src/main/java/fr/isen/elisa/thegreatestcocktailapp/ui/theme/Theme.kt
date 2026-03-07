package fr.isen.elisa.thegreatestcocktailapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val AppColorScheme = lightColorScheme(
    primary = OrangeMain,
    secondary = OrangeSoft,
    background = PeachBackground,
    surface = CreamCard,
    onPrimary = WhiteSoft,
    onSecondary = BrownText,
    onBackground = BrownText,
    onSurface = BrownText
)

@Composable
fun TheGreatestCocktailAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content
    )
}