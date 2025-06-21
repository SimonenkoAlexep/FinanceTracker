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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.ui.ListItem.HeaderListItem
import com.lsimanenka.financetracker.ui.ListItem.IconButtonTrail
import com.lsimanenka.financetracker.ui.ListItem.ListItem
import com.lsimanenka.financetracker.ui.theme.LightColors
import com.lsimanenka.financetracker.ui.viewmodel.ExpensesHistoryViewModel
import com.lsimanenka.financetracker.ui.viewmodel.TransactionListState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@SuppressLint("NewApi")
@Composable
fun ExpensesHistoryScreen(
    accountId: Int,
    onTransactionClick: (Int) -> Unit = {},
    viewModel: ExpensesHistoryViewModel = hiltViewModel()
) {
    // Передаём VM номер счета при старте
    LaunchedEffect(accountId) {
        viewModel.setAccountId(accountId)
    }

    // Слушаем текущее состояние экрана
    val uiState by viewModel.state

    // Слушаем диапазон дат из VM
    val startDate by viewModel.startDate.collectAsState()
    val endDate   by viewModel.endDate.collectAsState()

    // Локальные стейты для показа DatePickerDialog
    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker   by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val dateFmt = DateTimeFormatter.ofPattern("dd MMM yyyy")

    // DatePickerDialog для выбора начала
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

    // DatePickerDialog для выбора конца
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
        // 1) Заголовки с выбором дат и общей суммой
        HeaderListItem(
            content      = "Начало",
            trailContent = startDate.format(dateFmt),
            trail        = { IconButtonTrail(R.drawable.ic_more_vert) },
            onClick      = { showStartPicker = true }
        )
        HeaderListItem(
            content      = "Конец",
            trailContent = endDate.format(dateFmt),
            trail        = { IconButtonTrail(R.drawable.ic_more_vert) },
            onClick      = { showEndPicker = true }
        )
        // Считаем сумму уже отфильтрованных транзакций (если есть)
        if (uiState.transactions.isNotEmpty()) {
            val total = uiState.transactions
                .sumOf { it.amount.toDoubleOrNull() ?: 0.0 }
            HeaderListItem(
                content = "Сумма",
                money   = "%,.2f".format(total),
                color   = LightColors.secondary.copy(alpha = 0.1f)
            )
        }
        Spacer(Modifier.height(8.dp))

        // 2) Основной контент: загрузка, ошибка или список
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
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.transactions) { tx ->
                        ListItem(
                            lead         = "\u20BD",
                            content      = tx.comment ?: "Комментариев нет",
                            comment      = null,
                            money        = tx.amount,
                            trailContent = tx.transactionDate.substring(11, 16),
                            trail        = { IconButtonTrail(R.drawable.ic_more_vert) },
                            onClick      = { onTransactionClick(tx.id) }
                        )
                    }
                }
            }
        }
    }
}
