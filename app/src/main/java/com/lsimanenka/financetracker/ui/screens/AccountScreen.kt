package com.lsimanenka.financetracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lsimanenka.financetracker.R
//import androidx.hilt.navigation.compose.hiltViewModel
import com.lsimanenka.financetracker.ui.utils.list_item.HeaderListItem
import com.lsimanenka.financetracker.ui.theme.MyColors
import com.lsimanenka.financetracker.domain.viewmodel.AccountViewModel
import com.lsimanenka.financetracker.domain.viewmodel.CategoriesViewModel
import com.lsimanenka.financetracker.ui.LocalAppComponent

@Composable
fun AccountScreen() {

    val factory = LocalAppComponent.current.viewModelFactory().get()
    val viewModel: AccountViewModel = viewModel(factory = factory)
    val viewModelCategory: CategoriesViewModel = viewModel(factory = factory)

    val uiState by viewModel.state


    Column(Modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            uiState.error.isNotBlank() -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("${stringResource(R.string.account_error)}: ${uiState.error}")
                }
            }

            uiState.account != null -> {
                HeaderListItem(
                    lead = "💰",
                    content = stringResource(R.string.account_balance),
                    money = uiState.account!!.balance,
                    color = MyColors.surface
                )
                HeaderListItem(
                    content = stringResource(R.string.account_currency),
                    money = "",
                    color = MyColors.surface
                )
            }

            else -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.no_data))
                }
            }
        }
    }
}
