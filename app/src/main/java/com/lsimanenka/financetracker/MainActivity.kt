package com.lsimanenka.financetracker

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.lsimanenka.financetracker.ui.bottomNavigationBar.BottomNavigationBar
import com.lsimanenka.financetracker.ui.navigation.MyNavHost
import com.lsimanenka.financetracker.ui.navigation.NavItem
import com.lsimanenka.financetracker.ui.navigation.Routes
import com.lsimanenka.financetracker.ui.splash.LottieSplashScreen
import com.lsimanenka.financetracker.ui.topAppBar.TopBarFor
import com.lsimanenka.financetracker.ui.theme.LightColors
import dagger.hilt.android.AndroidEntryPoint


/*@Composable
fun AppEntry() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "splash") {
        composable("splash") {
            LottieSplashScreen {
                navController.navigate("main") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
        composable("main") {
            FinanceTracker()
        }
    }
}
*/

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


/*@Composable
fun FinanceTracker() {
    val navController = rememberNavController()
    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route

    Scaffold(
        topBar = TopBarFor(navController, currentRoute),

        bottomBar = { BottomNavigationBar(navController) },

        floatingActionButton = {
            if (currentRoute == NavItem.EXPENSES.route || currentRoute == NavItem.INCOME.route || currentRoute == NavItem.ACCOUNT.route) {
                FloatingActionButton(
                    onClick = { /*…*/ },
                    containerColor = LightColors.primary,
                    contentColor = LightColors.onPrimary,
                    modifier = Modifier.size(56.dp),
                    shape = CircleShape
                ) { Icon(Icons.Default.Add, "Добавить", modifier = Modifier.size(31.dp)) }
            }
        },
        floatingActionButtonPosition = FabPosition.End

    ) { innerPadding ->
        MyNavHost(
            navController = navController,
            startDest = NavItem.EXPENSES,
            modifier = Modifier.padding(innerPadding)
        )
    }
}*/

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



