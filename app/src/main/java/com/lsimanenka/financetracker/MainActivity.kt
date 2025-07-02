package com.lsimanenka.financetracker

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lsimanenka.financetracker.ui.bottomNavigationBar.BottomNavigationBar
import com.lsimanenka.financetracker.ui.navigation.MyNavHost
import com.lsimanenka.financetracker.ui.navigation.Routes
import com.lsimanenka.financetracker.ui.theme.LightColors
import com.lsimanenka.financetracker.ui.topAppBar.TopBarAction
import com.lsimanenka.financetracker.ui.topAppBar.TopBarFor
import com.lsimanenka.financetracker.domain.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import com.lsimanenka.financetracker.domain.viewmodel.AccountEditViewModel
import com.lsimanenka.financetracker.ui.AppEntry

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent {
            AppEntry()
        }
    }
}

