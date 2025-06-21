package com.lsimanenka.financetracker.ui.bottomNavigationBar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.ui.navigation.NavItem
import com.lsimanenka.financetracker.ui.navigation.Routes
import com.lsimanenka.financetracker.ui.theme.LightColors

data class BottomNavItem(val route: String, val label: String, val icon: Int)

fun resolveSelectedTab(currentRoute: String?): String? {
    return when (currentRoute) {
        Routes.EXPENSES_HISTORY -> Routes.EXPENSES
        else -> currentRoute
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route
    val resolvedRoute = resolveSelectedTab(currentRoute)


    NavigationBar {
        listOf(
            BottomNavItem(Routes.EXPENSES, "Расходы", R.drawable.ic_expenses),
            BottomNavItem(Routes.INCOME, "Доходы", R.drawable.ic_income),
            BottomNavItem(Routes.ACCOUNT, "Счёт", R.drawable.ic_account),
            BottomNavItem(Routes.ITEMS, "Статьи", R.drawable.ic_items),
            BottomNavItem(Routes.SETTINGS, "Настройки", R.drawable.ic_settings),
        ).forEach { (route, label, iconRes) ->
            val selected = route == resolvedRoute
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(iconRes),
                        contentDescription = null
                    )
                },
                label = { Text(text = label, fontSize = 11.sp) },
                selected = selected,
                onClick = {
                    if (currentRoute != route) {
                        navController.navigate(route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = LightColors.primary,
                    selectedTextColor = LightColors.onSecondary,
                    indicatorColor = LightColors.secondary,
                    unselectedIconColor = LightColors.onSecondary,
                    unselectedTextColor = LightColors.onSecondary,
                    disabledIconColor = LightColors.onPrimary,
                    disabledTextColor = LightColors.onPrimary
                )
            )
        }
    }
}


/*@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavItem.entries.forEach { item ->
            val isSelected = item.route == currentRoute

            NavigationBarItem(

                icon = {
                    item.icon?.let {
                        Icon(
                            imageVector = ImageVector.vectorResource(it),
                            contentDescription = item.label
                        )
                    }
                },
                label = {
                    item.label?.let {
                        Text(
                            text = item.label,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.SansSerif
                        )
                    }
                },
                selected = isSelected,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
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
}*/
