package com.lsimanenka.financetracker.ui.screens

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.lsimanenka.financetracker.R
import com.lsimanenka.financetracker.domain.viewmodel.TransactionViewModel
import com.lsimanenka.financetracker.ui.utils.list_item.ListItem
import com.lsimanenka.financetracker.ui.LocalAppComponent
import com.lsimanenka.financetracker.ui.components.CategoryDropdown
import com.lsimanenka.financetracker.ui.theme.MyColors
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionScreen(
    transactionId: Int?,
    registerSave: (() -> Unit) -> Unit,
    navController: NavHostController,
) {
    val factory = LocalAppComponent.current.viewModelFactory().get()
    val viewModel: TransactionViewModel = viewModel(factory = factory)

    LaunchedEffect(transactionId) {
        viewModel.init(transactionId)
    }

    val state by viewModel.uiState.collectAsState()

    SideEffect {
        registerSave {
            viewModel.save {
                navController.popBackStack()
            }
        }
    }

    Column(
        Modifier
            .fillMaxWidth()
            .background(color = MyColors.background)
    ) {
        ListItem(
            content = stringResource(R.string.account_label),
            trailContent = state.accountId.toString(),
        )
        CategoryDropdown(
            selectedId = state.categoryId,
            onSelect = { viewModel.onCategoryIdChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .background(MyColors.background)
                .padding(top = 8.dp)
        )
        OutlinedTextField(
            value = state.amount,
            onValueChange = viewModel::onAmountChange,
            label = { Text(stringResource(R.string.amount_label)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        DatePickerField(date = state.date, onDateChange = viewModel::onDateChange)
        TimePickerField(time = state.time, onTimeChange = viewModel::onTimeChange)

        OutlinedTextField(
            value = state.description.orEmpty(),
            onValueChange = viewModel::onDescriptionChange,
            label = { Text(stringResource(R.string.comment_label)) },
            placeholder = { Text(stringResource(R.string.comment_placeholder)) },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 64.dp),
            singleLine = false,
            maxLines = 4,
            keyboardOptions = KeyboardOptions.Default
        )

        if (transactionId != null) {
            Button(onClick = {
                viewModel.delete {
                    navController.popBackStack()
                }
            }) {
                Text(stringResource(R.string.delete))
            }
        }
    }
}

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    date: LocalDate,
    onDateChange: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val formatted = remember(date) {
        date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    }

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        android.app.DatePickerDialog(
            context,
            { _, y, m, d ->
                onDateChange(LocalDate.of(y, m + 1, d))
                showDialog = false
            },
            date.year, date.monthValue - 1, date.dayOfMonth
        ).show()
    }

    OutlinedTextField(
        value = formatted,
        onValueChange = {},
        readOnly = true,
        label = { Text(stringResource(R.string.date_label)) },
        trailingIcon = {
            Icon(
                Icons.Default.DateRange,
                contentDescription = null,
                modifier = Modifier.clickable { showDialog = true }
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .clickable { showDialog = true }
    )
}


@SuppressLint("NewApi")
@Composable
fun TimePickerField(
    time: LocalTime,
    onTimeChange: (LocalTime) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val formatted = remember(time) {
        time.format(DateTimeFormatter.ofPattern("HH:mm"))
    }

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        TimePickerDialog(
            context,
            { _, h, min ->
                onTimeChange(LocalTime.of(h, min))
                showDialog = false
            },
            time.hour, time.minute, true
        ).show()
    }

    OutlinedTextField(
        value = formatted,
        onValueChange = {},
        readOnly = true,
        label = { Text(stringResource(R.string.time_label)) },
        trailingIcon = {
            Icon(
                Icons.Default.DateRange,
                contentDescription = null,
                modifier = Modifier.clickable { showDialog = true }
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .clickable { showDialog = true }
    )
}


