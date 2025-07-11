package com.lsimanenka.financetracker.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
//import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lsimanenka.financetracker.ui.utils.list_item.IconButtonTrail
import com.lsimanenka.financetracker.ui.utils.list_item.HeaderListItem
import com.lsimanenka.financetracker.ui.utils.list_item.ListItem
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.domain.viewmodel.ExpensesViewModel
import com.lsimanenka.financetracker.ui.LocalAppComponent

@Composable
fun ExpensesScreen(
    onTransactionClick: (Int) -> Unit = {},
    isIncome: Boolean
) {

    val factory = LocalAppComponent.current.viewModelFactory().get()
    val viewModel: ExpensesViewModel = viewModel(factory = factory)

    //viewModel.setIsIncome(isIncome)
    val lifecycleOwner = LocalLifecycleOwner.current

    // Навешиваем Observer, чтобы на каждом onResume() делать запрос
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.setIsIncome(isIncome)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
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