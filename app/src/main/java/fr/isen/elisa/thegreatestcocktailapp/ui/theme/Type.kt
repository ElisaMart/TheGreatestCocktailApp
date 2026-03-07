package fr.isen.elisa.thegreatestcocktailapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import fr.isen.elisa.thegreatestcocktailapp.R

private val TeacherFont = FontFamily(
    Font(R.font.kgwhattheteacherwants)
)

val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = TeacherFont,
        fontSize = 34.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = TeacherFont,
        fontSize = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = TeacherFont,
        fontSize = 24.sp
    ),
    titleLarge = TextStyle(
        fontFamily = TeacherFont,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = TeacherFont,
        fontSize = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = TeacherFont,
        fontSize = 18.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = TeacherFont,
        fontSize = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = TeacherFont,
        fontSize = 16.sp
    )
)