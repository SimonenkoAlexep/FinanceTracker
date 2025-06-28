package com.lsimanenka.financetracker.ui.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

data class TransactionListState(
    val isLoading: Boolean = false,
    val transactions: List<TransactionResponse> = emptyList(),
    val startDate: String? = null,
    val endDate: String? = null,
    val error: String = ""
)

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class HistoryViewModel @Inject constructor(
    private val getTransactionUseCase: GetTransactionUseCase,
    accountAssistant: AccountAssistant
) : ViewModel() {

    private val _state = mutableStateOf(TransactionListState())
    val state: State<TransactionListState> = _state

    private val accountIdFlow: StateFlow<Int?> = accountAssistant.selectedAccountId
    private val isIncomeFlow = MutableStateFlow<Boolean?>(null)

    private val _startDate = MutableStateFlow(LocalDate.now().withDayOfMonth(1))
    private val _endDate   = MutableStateFlow(LocalDate.now())

    val startDate: StateFlow<LocalDate> = _startDate
    val endDate:   StateFlow<LocalDate> = _endDate

    init {
        viewModelScope.launch {
            accountIdFlow
                .filterNotNull()
                .flatMapLatest { accountId ->
                    isIncomeFlow
                        .filterNotNull()
                        .flatMapLatest { isIncome ->
                            combine(_startDate, _endDate) { start, end ->
                                Triple(accountId, start, end to isIncome)
                            }
                        }
                }
                .flatMapLatest { (accountId, start, endAndIncome) ->
                    val (end, isIncome) = endAndIncome
                    loadTransactionsFlow(accountId, start, end, isIncome)
                }
                .onEach { result ->
                    when (result) {
                        is Resource.Loading -> _state.value =
                            TransactionListState(isLoading = true)
                        is Resource.Success -> _state.value =
                            TransactionListState(
                                isLoading = false,
                                transactions = result.data.orEmpty()
                            )
                        is Resource.Error -> _state.value =
                            TransactionListState(
                                isLoading = false,
                                error = result.message ?: "Unknown error"
                            )
                    }
                }
                .launchIn(this)

        }
    }

    fun setIsIncome(isIncome: Boolean) {
        isIncomeFlow.value = isIncome
    }

    fun onStartDatePicked(newDate: LocalDate) {
        _startDate.value = newDate
    }

    fun onEndDatePicked(newDate: LocalDate) {
        _endDate.value = newDate
    }

    private fun loadTransactionsFlow(
        accountId: Int,
        start: LocalDate,
        end: LocalDate,
        isIncome: Boolean
    ): Flow<Resource<List<TransactionResponse>>> {
        val fmt = DateTimeFormatter.ISO_LOCAL_DATE
        val startStr = start.format(fmt)
        val endStr   = end.format(fmt)
        return getTransactionUseCase(accountId, startStr, endStr, isIncome)
    }
}
