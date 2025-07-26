package com.lsimanenka.financetracker

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
//import dagger.hilt.android.AndroidEntryPoint
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.lsimanenka.financetracker.domain.viewmodel.AccountEditViewModel
import com.lsimanenka.financetracker.ui.AppEntry
import com.lsimanenka.financetracker.ui.LocalAppComponent
import com.lsimanenka.financetracker.ui.theme.MyColors
import com.lsimanenka.financetracker.ui.theme.ThemeManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT



        setContent {

            val isDarkTheme by rememberSaveable { mutableStateOf(ThemeManager.loadTheme(applicationContext)) }
            var primaryColor by rememberSaveable { mutableStateOf(ThemeManager.loadPrimaryColor(applicationContext)) }
            MyColors.init(isDarkTheme, primaryColor)
            val appComponent = (application as FinanceTrackerApplication).appComponent

            CompositionLocalProvider(LocalAppComponent provides appComponent) {
                AppEntry()
            }

        }
    }
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(ThemeManager.updateLocale(base))
    }
}

