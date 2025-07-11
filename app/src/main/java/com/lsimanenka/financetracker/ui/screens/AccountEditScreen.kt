package com.lsimanenka.financetracker.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lsimanenka.financetracker.domain.viewmodel.AccountEditViewModel
import com.lsimanenka.financetracker.ui.utils.list_item.HeaderListItem
import com.lsimanenka.financetracker.ui.utils.list_item.ListItem
import com.lsimanenka.financetracker.ui.LocalAppComponent
import com.lsimanenka.financetracker.ui.navigation.Routes


@SuppressLint("UnrememberedGetBackStackEntry")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountEditScreen(
    registerSave: ( () -> Unit ) -> Unit,
    navController: NavHostController
) {
    //val navController = LocalNavController.current
    val entry = remember { navController.getBackStackEntry(Routes.ACCOUNT_EDIT) }
    val factory = LocalAppComponent.current.viewModelFactory().get()
    val viewModel: AccountEditViewModel = viewModel(
        viewModelStoreOwner = entry,
        factory             = factory
    )

    // Регистрируем функцию в родителе
    registerSave { viewModel.save { navController.navigate(Routes.ACCOUNT) } }

    val editState by viewModel.state
    Log.d("Edit State Before", editState.currency)
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
            Log.d("Edit State", "${editState.currency}")
            HeaderListItem(
                content = "Валюта",
                //money = "",
                trailContent = editState.currency,
                onClick = { showCurrencyDialog = true }
            )


            if (showCurrencyDialog) {
                ModalBottomSheet(
                    onDismissRequest = { showCurrencyDialog = false },
                    sheetState = sheetState,
                ) {
                    Column(Modifier.padding(16.dp)) {

                        listOf(
                            "₽" to "RUB",
                            "$" to "USD",
                            "€" to "EUR"
                        ).forEach { (symbol, currency) ->
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
