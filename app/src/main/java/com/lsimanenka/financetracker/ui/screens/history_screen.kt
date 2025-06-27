package com.lsimanenka.financetracker.ui.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.ui.ListItem.HeaderListItem
import com.lsimanenka.financetracker.ui.ListItem.IconButtonTrail
import com.lsimanenka.financetracker.ui.ListItem.ListItem
import com.lsimanenka.financetracker.ui.viewmodel.HistoryViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@SuppressLint("NewApi")
@Composable
fun HistoryScreen(
    onTransactionClick: (Int) -> Unit = {},
    viewModel: HistoryViewModel = hiltViewModel(),
    isIncome: Boolean
) {
    LaunchedEffect(Unit) {
        //viewModel.setAccountId(accountId)
        viewModel.setIsIncome(isIncome)
    }

    val uiState by viewModel.state

    val startDate by viewModel.startDate.collectAsState()
    val endDate by viewModel.endDate.collectAsState()

    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val dateFmt = DateTimeFormatter.ofPattern("dd MMM yyyy")

    if (showStartPicker) {
        val cal = Calendar.getInstance().apply {
            set(startDate.year, startDate.monthValue - 1, startDate.dayOfMonth)
        }
        DatePickerDialog(
            context,
            { _, y, m, d ->
                viewModel.onStartDatePicked(LocalDate.of(y, m + 1, d))
                showStartPicker = false
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).apply {
            setOnDismissListener { showStartPicker = false }
        }.show()
    }

    if (showEndPicker) {
        val cal = Calendar.getInstance().apply {
            set(endDate.year, endDate.monthValue - 1, endDate.dayOfMonth)
        }
        DatePickerDialog(
            context,
            { _, y, m, d ->
                viewModel.onEndDatePicked(LocalDate.of(y, m + 1, d))
                showEndPicker = false
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).apply {
            setOnDismissListener { showEndPicker = false }
        }.show()
    }

    Column(Modifier.fillMaxSize()) {
        HeaderListItem(
            content = "Начало",
            trailContent = startDate.format(dateFmt),
            onClick = { showStartPicker = true }
        )
        HeaderListItem(
            content = "Конец",
            trailContent = endDate.format(dateFmt),
            onClick = { showEndPicker = true }
        )

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
                            trailContent = tx.transactionDate.substring(11, 16),
                            trail = { IconButtonTrail(R.drawable.ic_more_vert) },
                            onClick = { onTransactionClick(tx.id) }
                        )
                    }
                }
            }
        }
    }
}
