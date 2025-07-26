package com.lsimanenka.financetracker.ui.theme

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

object ThemeManager {
    private const val PREF_NAME = "theme_pref"
    private const val KEY_DARK_MODE = "is_dark_mode"

    fun saveTheme(context: Context, isDark: Boolean) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_DARK_MODE, isDark).apply()
    }

    fun loadTheme(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_DARK_MODE, false)
    }

    private const val KEY_PRIMARY_COLOR = "key_primary_color"

    fun savePrimaryColor(context: Context, color: PrimaryColor) {
        context.getSharedPreferences("theme_pref", Context.MODE_PRIVATE)
            .edit()
            .putInt(KEY_PRIMARY_COLOR, color.ordinal)
            .apply()
    }

    fun loadPrimaryColor(context: Context): PrimaryColor {
        val ordinal = context.getSharedPreferences("theme_pref", Context.MODE_PRIVATE)
            .getInt(KEY_PRIMARY_COLOR, 0)
        return PrimaryColor.fromOrdinal(ordinal)
    }

    private const val KEY_INTERVAL = "sync_interval_hours"
    //private const val PREF_NAME = "sync_settings"

    fun saveInterval(context: Context, hours: Int) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit().putInt(KEY_INTERVAL, hours).apply()
    }

    fun loadInterval(context: Context): Int {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_INTERVAL, 3)
    }

    private const val KEY_ENABLED = "vibration_enabled"
    private const val KEY_EFFECT = "vibration_effect"

    fun isVibrationEnabled(context: Context): Boolean =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_ENABLED, true)

    fun setVibrationEnabled(context: Context, enabled: Boolean) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit().putBoolean(KEY_ENABLED, enabled).apply()
    }

    fun loadEffect(context: Context): HapticEffect {
        val ordinal = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_EFFECT, 0)
        return HapticEffect.fromOrdinal(ordinal)
    }

    fun setEffect(context: Context, effect: HapticEffect) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit().putInt(KEY_EFFECT, effect.ordinal).apply()
    }

    private const val KEY_LOCALE = "selected_locale"

    fun saveLocale(context: Context, locale: String) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit().putString(KEY_LOCALE, locale).apply()
    }

    fun getLocale(context: Context): Locale {
        val code = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(KEY_LOCALE, "ru") ?: "ru"
        return Locale(code)
    }

    fun updateLocale(context: Context): Context {
        val locale = getLocale(context)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        return context.createConfigurationContext(config)
    }

}


fun toggleTheme(context: Context, value: Boolean) {
    ThemeManager.saveTheme(context, value)
    MyColors.init(value)
}

fun playHaptic(context: Context) {
    if (!ThemeManager.isVibrationEnabled(context)) return

    val effect = ThemeManager.loadEffect(context)
    val vibrator = context.getSystemService(Vibrator::class.java)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(effect.duration, effect.amplitude))
    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(effect.duration)
    }
}
