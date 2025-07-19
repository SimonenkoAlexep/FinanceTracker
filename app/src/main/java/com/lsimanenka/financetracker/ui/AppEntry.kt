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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lsimanenka.financetracker.di.AppComponent
//import com.lsimanenka.financetracker.domain.viewmodel.AccountEditViewModel
import com.lsimanenka.financetracker.domain.viewmodel.MainActivityViewModel
import com.lsimanenka.financetracker.ui.bottom_navigation_bar.BottomNavigationBar
import com.lsimanenka.financetracker.ui.navigation.MyNavHost
import com.lsimanenka.financetracker.ui.navigation.Routes
import com.lsimanenka.financetracker.ui.theme.LightColors
import com.lsimanenka.financetracker.ui.top_app_bar.TopBarAction
import com.lsimanenka.financetracker.ui.top_app_bar.TopBarFor


val LocalAppComponent = staticCompositionLocalOf<AppComponent> {
    error("AppComponent not provided")
}

@Composable
fun AppEntry(
    //viewModel: MainActivityViewModel = hiltViewModel()

) {

    val factory = LocalAppComponent.current.viewModelFactory().get()
    val viewModel: MainActivityViewModel =
        androidx.lifecycle.viewmodel.compose.viewModel(factory = factory)

    val uiState by remember() { viewModel.state }

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route
    val baseRoute = route?.substringBefore("/")
    val accountId = uiState ?: 0
    var saveAction by remember { mutableStateOf<() -> Unit>({}) }
    /* val accountEditViewModel: AccountEditViewModel? =
         if (baseRoute == Routes.ACCOUNT_EDIT && backStackEntry != null) {
             val factory = LocalAppComponent.current.viewModelFactory().get()
             androidx.lifecycle.viewmodel.compose.viewModel(
                 factory =  factory
             )
         } else null*/

    val showChrome = route != Routes.SPLASH

    Scaffold(
        topBar = {
            if (showChrome) {
                TopBarFor(
                    currentRoute = route
                ) { action ->
                    when (action) {
                        TopBarAction.EditAccount -> {
                            Log.d("AAAAAAAAaa", "$accountId")
                            navController.navigate(Routes.ACCOUNT_EDIT)

                        }

                        TopBarAction.History -> {
                            if (baseRoute == Routes.EXPENSES) navController.navigate(Routes.EXPENSES_HISTORY)
                            if (baseRoute == Routes.INCOME) navController.navigate(Routes.INCOME_HISTORY)
                        }

                        TopBarAction.Cancel -> {
                            navController.popBackStack()
                        }

                        TopBarAction.Accept -> {
                            //accountEditViewModel?.save {
                            saveAction()
                            //navController.navigate(Routes.ACCOUNT)
                            //}
                        }

                        TopBarAction.Statistics -> {
                            if (baseRoute == Routes.EXPENSES_HISTORY) navController.navigate(Routes.EXPENSES_STATISTICS)
                            if (baseRoute == Routes.INCOME_HISTORY) navController.navigate(Routes.INCOME_STATISTICS)
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
                    onClick = { navController.navigate(Routes.TRANSACTION_CREATE) },
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
            modifier = Modifier.padding(innerPadding),
            registerSave = { action -> saveAction = action }
        )
    }
}
