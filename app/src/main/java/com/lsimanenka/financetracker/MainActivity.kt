package com.lsimanenka.financetracker

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent {
            FinanceTracker()
        }
    }
}

@Composable
fun FinanceTracker() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(navController, startDestination = "expenses", Modifier.padding(innerPadding)) {
            composable("expenses") { ExpensesScreen() }
            composable("income") { ExpensesScreen() }
            composable("account") { ExpensesScreen() }
            composable("items") { ExpensesScreen() }
            composable("settings") { ExpensesScreen() }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        NavItem("expenses", "Расходы", ImageVector.vectorResource(R.drawable.ic_expenses)),
        NavItem("income", "Доходы", ImageVector.vectorResource(R.drawable.ic_income)),
        NavItem("account", "Счёт", ImageVector.vectorResource(R.drawable.ic_account)),
        NavItem("items", "Статьи", ImageVector.vectorResource(R.drawable.ic_items)),
        NavItem("settings", "Настройки", ImageVector.vectorResource(R.drawable.ic_settings)),
    )

    var selectedItem by rememberSaveable { mutableStateOf(0) }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(text = item.label, fontSize = 11.sp, fontFamily = FontFamily.SansSerif) },
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


@Composable
fun LilListItem(lead : String? = null, content : String, money : String? = null, trail : String? = null, color : Color = Color.White) {
    Row(Modifier.background(color = color).height(56.dp).padding(16.dp), verticalAlignment = Alignment.CenterVertically ) {
        if (lead != null) {
            Text(lead, fontSize = 16.sp, modifier =  Modifier.padding(4.dp))
        }
        Text(content, fontSize = 16.sp, modifier =  Modifier.padding(4.dp))
        Spacer(Modifier.weight(1f))
        if (money != null) {
            Text(money+"₽", fontSize = 16.sp, modifier =  Modifier.padding(start = 8.dp, end = 8.dp))
        }
        if (trail != null) {
            Text(trail, fontSize = 16.sp, modifier =  Modifier.padding(start = 8.dp, end = 8.dp))
        }
    }
}

@Composable
fun ListItem(lead : String? = null, content : String, comment : String? = null, money : String? = null, trail : String? = null) {
    Row(Modifier.height(70.dp).border(1.dp, Color.Gray).padding(16.dp), verticalAlignment = Alignment.CenterVertically ) {
        if (lead != null) {
            Text(lead, fontSize = 16.sp, modifier =  Modifier.padding(4.dp))
        }
        if (comment == null) {
            Text(content, fontSize = 16.sp, modifier =  Modifier.padding(4.dp))
        } else {
            Column(modifier =  Modifier.padding(end = 4.dp, start = 4.dp)) {
                Text(content, fontSize = 16.sp)
                Text(comment, fontSize = 14.sp, color = Color(0xFF49454F))
            }
        }
        Spacer(Modifier.weight(1f))
        if (money != null) {
            Text(money+"₽", fontSize = 16.sp, modifier =  Modifier.padding(8.dp))
        }
        if (trail != null) {
            Text(trail, fontSize = 16.sp, modifier =  Modifier.padding(8.dp))
        }
    }
}


@Composable
fun ExpensesScreen() {
    Column {
        MyTopAppBar("Расходы сегодня", painterResource(R.drawable.ic_history), "История")
        LilListItem( content = "Всего", money = "436558", color = LightColors.secondary)
        ListItem("S", "Аренда квартиры", null, "100000", "S")
        ListItem("S", "Аренда квартиры", null, "100000", "S")
        ListItem("S", "Аренда квартиры", "Harold", "100000", "S")
        ListItem("S", "Аренда квартиры", "Anny", "100000", "S")
        ListItem("S", "Аренда квартиры", null, "100000", "S")
        ListItem("S", "Аренда квартиры", "Alex", "100000", "S")
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(title: String, painter : Painter?, iconDescription : String?) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = LightColors.primary
        )
    }
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(32.dp))

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center
                    )
                }

                if (painter != null) {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.padding(end = 8.dp, top = 8.dp)
                    ) {
                        Icon(
                            painter = painter,
                            contentDescription = iconDescription
                        )
                    }
                }
            }
        },
        modifier = Modifier.width(412.dp).height(64.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = LightColors.primary
        )
    )
}

data class NavItem(val route: String, val label: String, val icon: ImageVector)

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    FinanceTracker()
}


private val LightColors = lightColorScheme(
    primary = Color(0xFF2AE881),
    secondary = Color(0xFFD4FAE6),
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black
)

