package com.lsimanenka.financetracker.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.lsimanenka.financetracker.ui.ListItem.HeaderListItem
import com.lsimanenka.financetracker.ui.ListItem.IconButtonTrail
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.ui.viewmodel.AccountViewModel
import com.lsimanenka.financetracker.ui.theme.LightColors

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
                val balance = uiState.account!!.balance.toDoubleOrNull()
                HeaderListItem(
                    lead = "\uD83D\uDCB0",
                    content = "Баланс",
                    money = if (balance != null) "%.2f".format(balance) else "—",
                    trail = { IconButtonTrail(R.drawable.ic_more_vert) },
                    color = LightColors.secondary
                )
                HeaderListItem(
                    content = "Валюта",
                    money = "",
                    trail = { IconButtonTrail(R.drawable.ic_more_vert) },
                    color = LightColors.secondary
                )
            }

            else -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Данных пока нет")
                }
            }
        }
    }
}
