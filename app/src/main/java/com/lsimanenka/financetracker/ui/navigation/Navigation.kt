package com.lsimanenka.financetracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lsimanenka.financetracker.ui.screens.ExpensesScreen
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.ui.screens.AccountScreen
import com.lsimanenka.financetracker.ui.screens.ExpensesHistoryScreen
import com.lsimanenka.financetracker.ui.screens.IncomeScreen
import com.lsimanenka.financetracker.ui.screens.ItemsScreen
import com.lsimanenka.financetracker.ui.screens.SettingsScreen
import com.lsimanenka.financetracker.ui.splash.LottieSplashScreen


enum class NavItem(val route: String, val label: String?, val icon: Int?) {
    EXPENSES("expenses", "Расходы", R.drawable.ic_expenses),
    INCOME("income", "Доходы", R.drawable.ic_income),
    ACCOUNT("account", "Счёт", R.drawable.ic_account),
    ITEMS("items", "Статьи", R.drawable.ic_items),
    SETTINGS("settings", "Настройки", R.drawable.ic_settings),
    EXPENSES_HISTORY("expenses_history", null, null),
   // SPLASH("splash", null, null)
}

@Composable
fun MyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController     = navController,
        startDestination  = Routes.SPLASH,
        modifier          = modifier
    ) {
        composable(Routes.SPLASH) {
            LottieSplashScreen {
                navController.navigate(Routes.EXPENSES) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            }
        }
        composable(Routes.EXPENSES_HISTORY) {
            ExpensesHistoryScreen(50)
        }
        composable(Routes.EXPENSES) {
            ExpensesScreen()
        }
        composable(Routes.INCOME) {
            IncomeScreen()
        }
        composable(Routes.ACCOUNT) {
            AccountScreen()
        }
        composable(Routes.ITEMS) {
            ItemsScreen()
        }
        composable(Routes.SETTINGS) {
            SettingsScreen()
        }
    }
}


/*@Composable
fun MyNavHost(navController: NavHostController, startDest: NavItem, modifier: Modifier) {
    NavHost(navController, startDestination = startDest.route, modifier = modifier) {
        NavItem.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    NavItem.ACCOUNT -> AccountScreen()
                    NavItem.EXPENSES -> ExpensesScreen()
                    NavItem.INCOME -> IncomeScreen()
                    NavItem.ITEMS -> ItemsScreen()
                    NavItem.SETTINGS -> SettingsScreen()
                    NavItem.EXPENSES_HISTORY  -> ExpensesHistoryScreen()
//                    NavItem.SPLASH -> LottieSplashScreen {
//                        navController.navigate(NavItem.EXPENSES.route) {
//                            popUpTo(NavItem.SPLASH.route) { inclusive = true }
//                        }
//                    }
                }
            }
        }

    }
}*/