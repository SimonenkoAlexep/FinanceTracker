package com.lsimanenka.financetracker.ui.viewmodel

import android.util.Log
import android.annotation.SuppressLint
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
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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
                Log.d("FinanceApp", "Транзакции результат: ${result.data}")
                _state.value = when(result) {
                    is Resource.Loading -> TransactionListState(isLoading = true)
                    is Resource.Success -> TransactionListState(transactions = result.data.orEmpty())
                    is Resource.Error   -> TransactionListState(error = result.message.orEmpty())
                }
            }
            .launchIn(viewModelScope)
        //Log.d("FinanceApp", "Selected account ID52: ${accountAssistant.selectedAccountId.value}")
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
            accountId   = accountId,
            isIncome    = isIncome,
            startDate   = today,
            endDate     = today
        )
    }
}



    /*fun setAccountId(accountId: Int) {
        _accountId.value = accountId
    }*/

    /*  fun onStartDatePicked(newDate: LocalDate) {
          _startDate.value = newDate
      }

      fun onEndDatePicked(newDate: LocalDate) {
          _endDate.value = newDate
      }*/

  /*  @SuppressLint("NewApi")
    private fun loadTransactionsFlow(
        accountId: Int,
        isIncome: Boolean
    ): Flow<Resource<List<TransactionResponse>>> {
        return getTransactionUseCase(
            accountId = accountId,
            isIncome = isIncome,
            startDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
            endDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        )
    }

    fun setIsIncome(isIncome: Boolean) {
        _isIncome.value = isIncome
    }
}*/
