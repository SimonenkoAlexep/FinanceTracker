package com.lsimanenka.financetracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lsimanenka.financetracker.ui.screens.ExpensesScreen
import com.lsimanenka.financetracker.ui.screens.AccountScreen
import com.lsimanenka.financetracker.ui.screens.HistoryScreen
import com.lsimanenka.financetracker.ui.screens.ItemsScreen
import com.lsimanenka.financetracker.ui.screens.SettingsScreen
import com.lsimanenka.financetracker.ui.splash.LottieSplashScreen

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
            HistoryScreen(isIncome = false)
        }
        composable(Routes.EXPENSES) {
            ExpensesScreen(isIncome = false)
        }
        composable(Routes.INCOME) {
            ExpensesScreen(isIncome = true)
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
        composable(Routes.INCOME_HISTORY) {
            HistoryScreen(isIncome = true)
        }
    }
}

