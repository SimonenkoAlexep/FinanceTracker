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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.lsimanenka.financetracker.ui.ListItem.IconButtonTrail
import com.lsimanenka.financetracker.ui.ListItem.HeaderListItem
import com.lsimanenka.financetracker.ui.ListItem.ListItem
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.ui.theme.LightColors
import com.lsimanenka.financetracker.ui.viewmodel.ExpensesViewModel
import com.lsimanenka.financetracker.ui.viewmodel.HistoryViewModel

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
    val context = LocalContext.current





    Column(Modifier.fillMaxSize()) {
        val total = uiState.transactions
            .sumOf { it.amount.toDoubleOrNull() ?: 0.0 }
       //Log.d("FinanceApp", "Selected account ID: ${uiState.transactions[0].category}")
        //Log.d("FinanceApp", "Selected account ID: ${uiState.transactions[0].category}")
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
                      //Log.d("FinanceApp", "Selected account ID Screen: ${tx.category}")
                    }
                    Log.d("FinanceApp", "Selected size Screen: ${uiState.transactions.size}")
                }
            }
        }
    }







 /*   Column {







        HeaderListItem(content = "Всего", money = "436558", color = LightColors.secondary)
        ListItem(
            lead = "\uD83D\uDC80",
            content = "Аренда квартиры",
            money = "100000",
            trail = { IconButtonTrail(R.drawable.ic_more_vert) })
        ListItem(
            lead = "\uD83C\uDFE0",
            content = "Аренда квартиры",
            comment = null,
            money = "100000",
            trail = { IconButtonTrail(R.drawable.ic_more_vert) })
        ListItem(
            lead = "\uD83C\uDFE0",
            content = "Аренда квартиры",
            comment = "Harold",
            money = "100000",
            trail = { IconButtonTrail(R.drawable.ic_more_vert) })
        ListItem(
            lead = "\uD83C\uDFE0",
            content = "Аренда квартиры",
            comment = "Anny",
            money = "100000",
            trail = { IconButtonTrail(R.drawable.ic_more_vert) })
        ListItem(
            lead = "\uD83C\uDFE0",
            content = "Аренда квартиры",
            comment = null,
            money = "100000",
            trail = { IconButtonTrail(R.drawable.ic_more_vert) })
        ListItem(
            lead = "\uD83C\uDFE0",
            content = "Аренда квартиры",
            comment = "Alex",
            money = "100000",
            trail = { IconButtonTrail(R.drawable.ic_more_vert) })

    }

*/
}