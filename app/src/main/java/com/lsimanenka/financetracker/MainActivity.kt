package com.lsimanenka.financetracker

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
//import dagger.hilt.android.AndroidEntryPoint
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.lsimanenka.financetracker.domain.viewmodel.AccountEditViewModel
import com.lsimanenka.financetracker.ui.AppEntry
import com.lsimanenka.financetracker.ui.LocalAppComponent

//@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        // в MainActivity.onCreate()
        setContent {
            val appComponent = (application as FinanceTrackerApplication).appComponent

            CompositionLocalProvider(LocalAppComponent provides appComponent) {
                AppEntry()
            }
        }

    }
}

