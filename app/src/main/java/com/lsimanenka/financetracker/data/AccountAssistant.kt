package com.lsimanenka.financetracker.data

import android.util.Log
import com.lsimanenka.financetracker.common.Resource
import com.lsimanenka.financetracker.data.model.Account
import com.lsimanenka.financetracker.data.model.AccountResponse
import com.lsimanenka.financetracker.data.use_case.GetAccountUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountAssistant @Inject constructor(
    private val getAccountsUseCase: GetAccountUseCase
) {
    private val _selectedId = MutableStateFlow<Int?>(null)
    val selectedAccountId: StateFlow<Int?> = _selectedId.asStateFlow()

    init {
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            val firstId = getAccountsUseCase()
                .filterIsInstance<Resource.Success<List<Account>>>()
                .mapNotNull { it.data?.firstOrNull()?.id }
                .firstOrNull()
            firstId?.let {
                _selectedId.value = it
            }
        }
    }

}
