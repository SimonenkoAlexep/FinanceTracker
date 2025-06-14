package com.lsimanenka.financetracker

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.lsimanenka.financetracker.ListItem.LilListItem
import com.lsimanenka.financetracker.ListItem.ListItem
import com.lsimanenka.financetracker.navigation.MyNavHost
import com.lsimanenka.financetracker.navigation.NavItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LottieSplashScreen(
    onFinished: () -> Unit
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.splash_animation)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1,
        restartOnPlay = false,
        speed = 1f
    )

    LaunchedEffect(progress) {
        if (progress == 1f) {
            onFinished()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightColors.primary),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier
                .size(200.dp)
        )
    }
}


@Composable
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

class MainActivity : ComponentActivity() {

    private var isReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        //WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AppEntry()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceTracker() {
    val navController = rememberNavController()
    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route

    Scaffold(
        topBar = TopBarFor(currentRoute),

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
}


@Composable
fun TopBarFor(route: String?): @Composable () -> Unit = when (route) {
    NavItem.EXPENSES.route -> {
        { MyTopAppBar("Расходы сегодня", painterResource(R.drawable.ic_history), "История") }
    }

    NavItem.INCOME.route -> {
        { MyTopAppBar("Доходы сегодня", painterResource(R.drawable.ic_history), "История") }
    }

    NavItem.ACCOUNT.route -> {
        { MyTopAppBar("Мой счёт", painterResource(R.drawable.ic_edit), "Редактировать") }
    }

    NavItem.ITEMS.route -> {
        { MyTopAppBar("Мои статьи", null, null) }
    }

    NavItem.SETTINGS.route -> {
        { MyTopAppBar("Настройки", null, null) }
    }

    else -> {
        { }
    }
}


@Composable
fun BottomNavigationBar(navController: NavHostController) {
    var selectedItem by rememberSaveable { mutableStateOf(0) }
    NavigationBar {
        NavItem.entries.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        ImageVector.vectorResource(item.icon),
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.SansSerif
                    )
                },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.route)
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = LightColors.primary,
                    selectedTextColor = LightColors.onSecondary,
                    selectedIndicatorColor = LightColors.secondary,
                    unselectedIconColor = LightColors.onSecondary,
                    unselectedTextColor = LightColors.onSecondary,
                    disabledIconColor = LightColors.onPrimary,
                    disabledTextColor = LightColors.onPrimary
                )
            )
        }
    }
}


//data class ListItemData(val lead : String?, val content : String, val comment : String?, val money : String?, val trail : String?)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    title: String,
    painter: Painter?,
    iconDescription: String?
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(LightColors.primary)
    }

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 22.sp,
                color = LightColors.onSecondary
            )
        },

        navigationIcon = {
            Spacer(Modifier.width(48.dp))
        },

        actions = {
            painter?.let {
                IconButton(onClick = { /*...*/ }) {
                    Icon(
                        painter = it,
                        contentDescription = iconDescription,
                        tint = LightColors.onSecondary
                    )
                }
            }
        },

        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = LightColors.primary,
            titleContentColor = LightColors.onSecondary
        )
    )
}


//data class NavItem(val route: String, val label: String, val icon: ImageVector)

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    FinanceTracker()
}


val LightColors = lightColorScheme(
    primary = Color(0xFF2AE881),
    secondary = Color(0xFFD4FAE6),
    background = Color.White,
    surface = Color(0xFFECE6F0),
    onPrimary = Color.White,
    onSecondary = Color.Black
)

