package com.lsimanenka.financetracker.ui.bottom_navigation_bar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.ui.navigation.Routes
import com.lsimanenka.financetracker.ui.theme.MyColors
import com.lsimanenka.financetracker.ui.theme.playHaptic

data class BottomNavItem(val route: String, val label: String, val icon: Int)

fun resolveSelectedTab(currentRoute: String?): String? {
    return when (currentRoute) {
        Routes.EXPENSES_HISTORY -> Routes.EXPENSES
        Routes.INCOME_HISTORY -> Routes.INCOME
        Routes.ACCOUNT_EDIT -> Routes.ACCOUNT
        Routes.INCOME_STATISTICS ->Routes.INCOME
        Routes.EXPENSES_STATISTICS -> Routes.EXPENSES
        else -> currentRoute
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val context = LocalContext.current
    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route
    val resolvedRoute = resolveSelectedTab(currentRoute)


    NavigationBar {
        listOf(
            BottomNavItem(Routes.EXPENSES, context.getString(R.string.nav_expenses), R.drawable.ic_expenses),
            BottomNavItem(Routes.INCOME, context.getString(R.string.nav_income), R.drawable.ic_income),
            BottomNavItem(Routes.ACCOUNT, context.getString(R.string.nav_account), R.drawable.ic_account),
            BottomNavItem(Routes.ITEMS, context.getString(R.string.nav_items), R.drawable.ic_items),
            BottomNavItem(Routes.SETTINGS, context.getString(R.string.nav_settings), R.drawable.ic_settings),
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
                    playHaptic(context)
                    if (currentRoute != route) {
                        navController.navigate(route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MyColors.primary,
                    selectedTextColor = MyColors.onSecondary,
                    indicatorColor = MyColors.secondary,
                    unselectedIconColor = MyColors.onSecondary,
                    unselectedTextColor = MyColors.onSecondary,
                    disabledIconColor = MyColors.onPrimary,
                    disabledTextColor = MyColors.onPrimary
                )
            )
        }
    }
}

