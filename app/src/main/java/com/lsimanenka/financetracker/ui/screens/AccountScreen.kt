package com.lsimanenka.financetracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lsimanenka.financetracker.ui.ListItem.HeaderListItem
import com.lsimanenka.financetracker.ui.topAppBar.TopBarAction
import com.lsimanenka.financetracker.ui.topAppBar.TopBarFor
import com.lsimanenka.financetracker.ui.navigation.Routes
import com.lsimanenka.financetracker.ui.theme.LightColors
import com.lsimanenka.financetracker.domain.viewmodel.AccountViewModel

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel()
) {
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
                    lead    = "💰",
                    content = "Баланс",
                    money   = uiState.account!!.balance,
                    color   = LightColors.surface
                )
                HeaderListItem(
                    content = "Валюта",
                    trailContent = uiState.account!!.currency,
                    color   = LightColors.surface
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
