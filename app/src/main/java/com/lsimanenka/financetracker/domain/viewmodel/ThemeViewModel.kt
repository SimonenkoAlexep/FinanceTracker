package com.lsimanenka.financetracker.domain.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.lsimanenka.financetracker.ui.theme.MyColors
import com.lsimanenka.financetracker.ui.theme.PrimaryColor
import com.lsimanenka.financetracker.ui.theme.ThemeManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ThemeViewModel(application: Application) : AndroidViewModel(application) {
    private val _isDarkTheme = MutableStateFlow(ThemeManager.loadTheme(application))
    private val _primaryColor = MutableStateFlow(ThemeManager.loadPrimaryColor(application))

    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme
    val primaryColor: StateFlow<PrimaryColor> = _primaryColor

    fun updateTheme(isDark: Boolean) {
        ThemeManager.saveTheme(getApplication(), isDark)
        _isDarkTheme.value = isDark
        MyColors.init(isDark, _primaryColor.value)
    }

    fun updatePrimaryColor(color: PrimaryColor) {
        ThemeManager.savePrimaryColor(getApplication(), color)
        _primaryColor.value = color
        MyColors.init(_isDarkTheme.value, color)
    }
}
