package com.lsimanenka.financetracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.lsimanenka.financetracker.ui.screens.*
import com.lsimanenka.financetracker.ui.splash.LottieSplashScreen

@Composable
fun MyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController    = navController,
        startDestination = Routes.SPLASH,
        modifier         = modifier
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
        composable(Routes.INCOME_HISTORY) {
            HistoryScreen(isIncome = true)
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
        composable(
            route = "${Routes.ACCOUNT_EDIT_BASE}/{accountId}",
            arguments = listOf(navArgument("accountId") { type = NavType.IntType })
        ) {
            AccountEditScreen()
        }
    }
}
