package com.lsimanenka.financetracker.ui.screens

import android.util.Log
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
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
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.setIsIncome(isIncome)
                viewModel.nextTrigger()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val uiState by viewModel.state
    val context = LocalContext.current

    Column(Modifier.fillMaxSize()) {
        val total = uiState.transactions.sumOf { it.amount.toDoubleOrNull() ?: 0.0 }

        HeaderListItem(
            content = stringResource(R.string.total),
            money = "%.0f".format(total)
        )

        when {
            uiState.isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            uiState.error.isNotBlank() -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.error_prefix) + ": ${uiState.error}")
                }
            }

            else -> {
                LazyColumn(Modifier.fillMaxSize()) {
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
