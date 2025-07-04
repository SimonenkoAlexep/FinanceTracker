package com.lsimanenka.financetracker.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.lsimanenka.financetracker.ui.ListItem.IconButtonTrail
import com.lsimanenka.financetracker.ui.ListItem.HeaderListItem
import com.lsimanenka.financetracker.ui.ListItem.ListItem
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.domain.viewmodel.ExpensesViewModel

@Composable
fun ExpensesScreen(
    onTransactionClick: (Int) -> Unit = {},
    viewModel: ExpensesViewModel = hiltViewModel(),
    isIncome: Boolean
) {

    LaunchedEffect(isIncome) {
        viewModel.setIsIncome(isIncome)
    }

    val uiState by viewModel.state

    Column(Modifier.fillMaxSize()) {
        val total = uiState.transactions
            .sumOf { it.amount.toDoubleOrNull() ?: 0.0 }
        HeaderListItem(
            content = "Сумма",
            money = "% .0f".format(total)
        )
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

            else -> {
                LazyColumn(
                    Modifier.fillMaxSize()
                ) {
                    items(uiState.transactions.reversed()) { tx ->
                        ListItem(
                            lead = tx.category.emoji,
                            content = tx.category.name,
                            comment = tx.comment,
                            money = tx.amount,
                            trail = { IconButtonTrail(R.drawable.ic_more_vert) },
                            onClick = { onTransactionClick(tx.id) }
                        )
                    }
                }
            }
        }
    }
}