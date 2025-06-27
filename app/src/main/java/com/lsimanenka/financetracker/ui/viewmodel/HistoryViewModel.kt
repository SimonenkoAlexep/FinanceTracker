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
    private val accountAssistant: AccountAssistant
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
        Log.d("FinanceApp", "Selected account ID2: ${accountIdFlow.value}")
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






/*package com.lsimanenka.financetracker.ui.viewmodel

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

data class Params(
    val start: LocalDate,
    val end: LocalDate,
    val isIncome: Boolean
)

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getTransactionUseCase: GetTransactionUseCase,
    private val accountAssistant: AccountAssistant
) : ViewModel() {

    private val _state = mutableStateOf(TransactionListState())
    val state: State<TransactionListState> = _state

    private val accountId = accountAssistant.selectedAccountId
    private val _isIncome = MutableStateFlow<Boolean?>(null)

    @SuppressLint("NewApi")
    private val _startDate = MutableStateFlow(LocalDate.now().withDayOfMonth(1))

    @SuppressLint("NewApi")
    private val _endDate = MutableStateFlow(LocalDate.now())

    val startDate: StateFlow<LocalDate> = _startDate.asStateFlow()
    val endDate: StateFlow<LocalDate> = _endDate.asStateFlow()

    init {
        combine(
            _startDate,
            _endDate,
            _isIncome.filterNotNull()
        ) { start, end, isIncome -> Params(start, end, isIncome) }
            .flatMapLatest { (start, end, isIncome) ->
                loadTransactionsFlow(accountId, start, end, isIncome)
            }
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = TransactionListState(isLoading = true)
                    }

                    is Resource.Success -> {
                        _state.value = TransactionListState(
                            isLoading = false,
                            transactions = result.data!!

                        )
                    }

                    is Resource.Error -> {
                        _state.value = TransactionListState(
                            isLoading = false,
                            error = result.message ?: "Unknown error"
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    /*fun setAccountId(accountId: Int) {
        _accountId.value = accountId
    }*/

    fun onStartDatePicked(newDate: LocalDate) {
        _startDate.value = newDate
    }

    fun onEndDatePicked(newDate: LocalDate) {
        _endDate.value = newDate
    }

    @SuppressLint("NewApi")
    private fun loadTransactionsFlow(
        accountId: Int,
        start: LocalDate,
        end: LocalDate,
        isIncome: Boolean
    ): Flow<Resource<List<TransactionResponse>>> {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE
        val startStr = start.format(formatter)
        val endStr = end.format(formatter)

        return getTransactionUseCase(
            accountId = accountId,
            startDate = startStr,
            endDate = endStr,
            isIncome = isIncome
        )
    }

    fun setIsIncome(isIncome: Boolean) {
        _isIncome.value = isIncome
    }
}
*/