package com.lsimanenka.financetracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lsimanenka.financetracker.screens.ExpensesScreen
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.screens.AccountScreen
import com.lsimanenka.financetracker.screens.IncomeScreen
import com.lsimanenka.financetracker.screens.ItemsScreen
import com.lsimanenka.financetracker.screens.SettingsScreen


enum class NavItem(val route: String, val label: String, val icon: Int) {
    EXPENSES("expenses", "Расходы", R.drawable.ic_expenses),
    INCOME("income", "Доходы", R.drawable.ic_income),
    ACCOUNT("account", "Счёт", R.drawable.ic_account),
    ITEMS("items", "Статьи", R.drawable.ic_items),
    SETTINGS("settings", "Настройки", R.drawable.ic_settings),
}

@Composable
fun MyNavHost(navController: NavHostController, startDest: NavItem, modifier: Modifier) {
    NavHost(navController, startDestination = startDest.route, modifier = modifier) {
        NavItem.entries.forEach { d ->
            composable(d.route) {
                when (d) {
                    NavItem.ACCOUNT -> AccountScreen()
                    NavItem.EXPENSES -> ExpensesScreen()
                    NavItem.INCOME -> IncomeScreen()
                    NavItem.ITEMS -> ItemsScreen()
                    NavItem.SETTINGS -> SettingsScreen()
                }
            }
        }

    }
}