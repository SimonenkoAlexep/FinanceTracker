package com.lsimanenka.financetracker

import android.content.pm.ActivityInfo
import android.os.Bundle
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
import androidx.navigation.compose.*
import androidx.compose.ui.unit.dp
import com.lsimanenka.financetracker.ui.bottomNavigationBar.BottomNavigationBar
import com.lsimanenka.financetracker.ui.navigation.MyNavHost
import com.lsimanenka.financetracker.ui.navigation.Routes
import com.lsimanenka.financetracker.ui.topAppBar.TopBarFor
import com.lsimanenka.financetracker.ui.theme.LightColors
import dagger.hilt.android.AndroidEntryPoint

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


@Composable
fun AppEntry() {
    val navController = rememberNavController()
    val navBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStack?.destination?.route

    val showChrome = currentRoute != Routes.SPLASH

    Scaffold(
        topBar = {
            if (showChrome) TopBarFor(navController, currentRoute)
        },
        bottomBar = {
            if (showChrome) BottomNavigationBar(navController)
        },
        floatingActionButton = {
            if (showChrome && (currentRoute == Routes.EXPENSES || currentRoute == Routes.INCOME || currentRoute == Routes.ACCOUNT)) {
                FloatingActionButton(
                    onClick = { /*…*/ },
                    modifier = Modifier.size(56.dp),
                    containerColor = LightColors.primary,
                    contentColor = LightColors.onPrimary,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, "Добавить", modifier = Modifier.size(31.dp))
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        MyNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}



