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
    modifier: Modifier = Modifier,
    registerSave: (() -> Unit) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH,
        modifier = modifier
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
            ExpensesScreen(
                isIncome = false,
                onTransactionClick = { txId ->
                    navController.navigate(Routes.navToEditTransaction(txId))
                }
            )
        }
        composable(Routes.INCOME) {
            ExpensesScreen(
                isIncome           = true,
                onTransactionClick = { txId ->
                    navController.navigate(Routes.navToEditTransaction(txId))
                }
            )
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
        composable(Routes.ACCOUNT_EDIT) {
            AccountEditScreen(registerSave, navController)
        }

        composable(Routes.TRANSACTION_CREATE) {
            TransactionScreen(
                transactionId = null,
                registerSave  = registerSave,
                navController = navController
            )
        }

        composable(
            route = Routes.TRANSACTION_EDIT_ROUTE,
            arguments = listOf(navArgument("transactionId") { type = NavType.IntType })
        ) { backStackEntry ->
            val txId = backStackEntry.arguments!!.getInt("transactionId")
            TransactionScreen(
                transactionId = txId,
                registerSave  = registerSave,
                navController = navController
            )
        }

        composable(Routes.EXPENSES_STATISTICS) {
            StatisticsScreen(isIncome = false)
        }
        composable(Routes.INCOME_STATISTICS) {
            StatisticsScreen(isIncome = true)
        }

    }
}
