package com.lsimanenka.financetracker.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lsimanenka.financetracker.ui.ListItem.HeaderListItem
import com.lsimanenka.financetracker.ui.ListItem.ListItem
import com.lsimanenka.financetracker.domain.viewmodel.AccountEditViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountEditScreen(
    viewModel: AccountEditViewModel = hiltViewModel()
) {
    val editState by  viewModel.state
    var showCurrencyDialog by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Column(Modifier.fillMaxSize()) {
        editState.original?.let {

            OutlinedTextField(
                value = editState.name,
                onValueChange = viewModel::onNameChange,
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Имя счёта") },
                singleLine = true,
            )


            OutlinedTextField(
                value = editState.draftBalance,
                onValueChange = viewModel::onBalanceChange,
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Баланс") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            HeaderListItem(
                content = "Валюта",
                money   = "",
                onClick = { showCurrencyDialog = true }
            )


            if (showCurrencyDialog) {
                ModalBottomSheet(
                    onDismissRequest = { showCurrencyDialog = false },
                    sheetState = sheetState,
                ) {
                    Column(Modifier.padding(16.dp)) {

                        listOf("₽" to "RUB", "$" to "USD", "€" to "EUR").forEach { (symbol, currency) ->
                            ListItem(

                                content = symbol + "  " + currency,
                                onClick = {
                                        viewModel.onCurrencyChange(symbol)
                                        showCurrencyDialog = false
                                    }
                            )
                        }
                        ListItem(
                            onClick = { showCurrencyDialog = false },
                            content = "Отмена",

                        )
                        Spacer(Modifier.height(16.dp))
                    }
                }
            }


            
        }
    }
}
