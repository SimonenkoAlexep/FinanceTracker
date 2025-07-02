package com.lsimanenka.financetracker.ui

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lsimanenka.financetracker.domain.viewmodel.AccountEditViewModel
import com.lsimanenka.financetracker.domain.viewmodel.MainActivityViewModel
import com.lsimanenka.financetracker.ui.bottomNavigationBar.BottomNavigationBar
import com.lsimanenka.financetracker.ui.navigation.MyNavHost
import com.lsimanenka.financetracker.ui.navigation.Routes
import com.lsimanenka.financetracker.ui.theme.LightColors
import com.lsimanenka.financetracker.ui.topAppBar.TopBarAction
import com.lsimanenka.financetracker.ui.topAppBar.TopBarFor


@Composable
fun AppEntry(
    viewModel: MainActivityViewModel = hiltViewModel()

) {
    val uiState by remember() { viewModel.state }

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route
    val baseRoute = route?.substringBefore("/")
    val accountId = uiState ?: 0
    val accountEditViewModel: AccountEditViewModel? =
        if (baseRoute == Routes.ACCOUNT_EDIT_BASE && backStackEntry != null) {
            hiltViewModel<AccountEditViewModel>(backStackEntry!!)
        } else null

    val showChrome = route != Routes.SPLASH

    Scaffold(
        topBar = {
            if (showChrome) {
                TopBarFor(
                    currentRoute = route
                ) { action ->
                    when (action) {
                        TopBarAction.Edit -> {
                            // переходим на Edit-экран, прокидываем id
                            Log.d("AAAAAAAAaa", "$accountId")
                            navController.navigate(Routes.accountEdit(accountId))

                        }

                        TopBarAction.History -> {
                            if (baseRoute == Routes.EXPENSES) navController.navigate(Routes.EXPENSES_HISTORY)
                            if (baseRoute == Routes.INCOME) navController.navigate(Routes.INCOME_HISTORY)
                        }

                        TopBarAction.Cancel -> {
                            navController.popBackStack()
                        }

                        TopBarAction.Accept -> {
                            accountEditViewModel?.save {
                                navController.navigate(Routes.ACCOUNT)
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            if (showChrome) BottomNavigationBar(navController)
        },
        floatingActionButton = {
            if (showChrome && (baseRoute == Routes.EXPENSES || baseRoute == Routes.INCOME)) {
                FloatingActionButton(
                    onClick = { /* навигация на экран добавления */ },
                    modifier = Modifier.size(56.dp),
                    containerColor = LightColors.primary,
                    contentColor = LightColors.onPrimary,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, "Добавить", Modifier.size(31.dp))
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
