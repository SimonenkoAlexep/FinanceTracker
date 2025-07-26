package com.lsimanenka.financetracker.ui.screens

import android.annotation.SuppressLint
import android.util.Log
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
import com.lsimanenka.financetracker.domain.viewmodel.AllTransactionViewModel
import com.lsimanenka.financetracker.domain.viewmodel.CategoriesViewModel
import com.lsimanenka.financetracker.domain.viewmodel.HistoryViewModel
import com.lsimanenka.financetracker.ui.LocalAppComponent
import com.lsimanenka.financetracker.ui.utils.list_item.AccountChart
import com.lsimanenka.financetracker.ui.utils.list_item.calculateDailyDiffs
import kotlinx.coroutines.flow.first

@SuppressLint("NewApi")
@Composable
fun AccountScreen() {

    val factory = LocalAppComponent.current.viewModelFactory().get()
    val viewModel: AccountViewModel = viewModel(factory = factory)
    val allTransactionViewModel : AllTransactionViewModel = viewModel(factory = factory)

    val uiState by viewModel.state

    //val incomeTx = remember { mutableStateOf<List<TransactionResponse>>(emptyList()) }
    //val expenseTx = remember { mutableStateOf<List<TransactionResponse>>(emptyList()) }

    val transactionState by allTransactionViewModel.state


    Log.d("ChartDebug", "Tx count: ${transactionState.transactions.size}, dates: ${transactionState.startDate} - ${transactionState.endDate}")


    val diffs = remember(transactionState.transactions) {
        calculateDailyDiffs(transactionState.transactions)
    }


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
                AccountChart(diffs)
            }

            else -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.no_data))
                }
            }
        }
    }
}
