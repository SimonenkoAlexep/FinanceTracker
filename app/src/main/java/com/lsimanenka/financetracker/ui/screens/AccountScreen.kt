package com.lsimanenka.financetracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.hilt.navigation.compose.hiltViewModel
import com.lsimanenka.financetracker.ui.utils.list_item.HeaderListItem
import com.lsimanenka.financetracker.ui.theme.LightColors
import com.lsimanenka.financetracker.domain.viewmodel.AccountViewModel
import com.lsimanenka.financetracker.ui.LocalAppComponent

@Composable
fun AccountScreen() {

    val factory = LocalAppComponent.current.viewModelFactory().get()
    val viewModel: AccountViewModel = viewModel(factory = factory)

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
                    Text("Ошибка: ${uiState.error}")
                }
            }

            uiState.account != null -> {
                HeaderListItem(
                    lead = "💰",
                    content = "Баланс",
                    money = uiState.account!!.balance,
                    color = LightColors.surface
                )
                HeaderListItem(
                    content = "Валюта",
                    money = "",
                    color = LightColors.surface
                )
            }

            else -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Данных нет")
                }
            }
        }
    }
}
