package com.lsimanenka.financetracker.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lsimanenka.financetracker.common.Resource
import com.lsimanenka.financetracker.data.AccountAssistant
import com.lsimanenka.financetracker.data.model.TransactionResponse
import com.lsimanenka.financetracker.data.use_case.GetTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val getTransactionUseCase: GetTransactionUseCase,
    accountAssistant: AccountAssistant
) : ViewModel() {

    private val _state = mutableStateOf(TransactionListState())
    val state: State<TransactionListState> = _state

    private val accountIdFlow = accountAssistant.selectedAccountId

    private val _isIncome = MutableStateFlow<Boolean?>(null)


    init {
        combine(
            accountIdFlow.filterNotNull(),
            _isIncome.filterNotNull()
        ) { accountId, isIncome ->
            accountId to isIncome
        }
            .flatMapLatest { (accountId, isIncome) ->
                loadTransactionsFlow(accountId, isIncome)
            }
            .onEach { result ->
                _state.value = when (result) {
                    is Resource.Loading -> TransactionListState(isLoading = true)
                    is Resource.Success -> TransactionListState(transactions = result.data.orEmpty())
                    is Resource.Error -> TransactionListState(error = result.message.orEmpty())
                }
            }
            .launchIn(viewModelScope)
    }

    fun setIsIncome(isIncome: Boolean) {
        _isIncome.value = isIncome
    }

    private fun loadTransactionsFlow(
        accountId: Int,
        isIncome: Boolean
    ): Flow<Resource<List<TransactionResponse>>> {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return getTransactionUseCase(
            accountId = accountId,
            isIncome = isIncome,
            startDate = today,
            endDate = today
        )
    }
}

