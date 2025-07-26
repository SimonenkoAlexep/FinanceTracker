package com.lsimanenka.financetracker.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

val LightColors = lightColorScheme(
    primary = Color(0xFF2AE881),
    secondary = Color(0xFFD4FAE6),
    background = Color.White,
    surface = Color(0xFFECE6F0),
    onPrimary = Color.White,
    onSecondary = Color.Black
)

private val DarkColors = darkColorScheme(
    primary   = Color(0xFF2AE881),
    secondary = Color(0xFF37955F),
    background= Color(0xFF121212),
    surface   = Color(0xFF1E1E1E),
    onPrimary = Color.Black,
    onSecondary = Color.White
)

object MyColors {

    // Текущая палитра — инициализируется при старте и обновлении темы
    private var palette = LightColors // по умолчанию

    // Метод инициализации палитры
    fun init(isDark: Boolean, primaryColor: PrimaryColor = PrimaryColor.GREEN) {
        palette = if (isDark) darkColorScheme(
            primary   = primaryColor.color,
            secondary = Color(0xFF37955F),
            background= Color(0xFF121212),
            surface   = Color(0xFF1E1E1E),
            onPrimary = Color.Black,
            onSecondary = Color.White
        ) else lightColorScheme(
            primary = primaryColor.color,
            secondary = Color(0xFFD4FAE6),
            background = Color.White,
            surface = Color(0xFFECE6F0),
            onPrimary = Color.White,
            onSecondary = Color.Black
        )
    }

    // Доступ к цветам напрямую
    val primary: Color get() = palette.primary
    val onPrimary: Color get() = palette.onPrimary
    val background: Color get() = palette.background
    val surface: Color get() = palette.surface
    val error: Color get() = palette.error
    val secondary: Color get() = palette.secondary
    val onBackground: Color get() = palette.onBackground
    val onSurface: Color get() = palette.onSurface
    val onSecondary: Color get() = palette.onSecondary
    val onError: Color get() = palette.onError

    val all get() = palette
}

enum class PrimaryColor(val color: Color) {
    GREEN(Color(0xFF2AE881)),
    BLUE(Color(0xFF2196F3)),
    RED(Color(0xFFF44336)),
    PURPLE(Color(0xFF9C27B0)),
    ORANGE(Color(0xFFFF9800));

    companion object {
        fun fromOrdinal(ordinal: Int) = values().getOrElse(ordinal) { GREEN }
    }
}

enum class HapticEffect(val duration: Long, val amplitude: Int, val label: String) {
    SHORT(100L, 128, "Короткий"),
    MEDIUM(150L, 180, "Средний"),
    STRONG(200L, 255, "Сильный");

    companion object {
        fun fromOrdinal(ord: Int): HapticEffect = values().getOrElse(ord) { SHORT }
    }
}


