package com.lsimanenka.financetracker.ui.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lsimanenka.financetracker.common.Resource
import com.lsimanenka.financetracker.data.repository.TransactionsRepository
import com.lsimanenka.financetracker.data.model.Transaction
import com.lsimanenka.financetracker.data.model.TransactionResponse
import com.lsimanenka.financetracker.data.model.mappers.toDomain
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
class ExpensesHistoryViewModel @Inject constructor(
    private val getTransactionUseCase: GetTransactionUseCase
) : ViewModel() {

    private val _state = mutableStateOf(TransactionListState())
    val state: State<TransactionListState> = _state

    private val _accountId = MutableStateFlow<Int?>(null)

    @SuppressLint("NewApi")
    private val _startDate = MutableStateFlow(LocalDate.now().withDayOfMonth(1))
    @SuppressLint("NewApi")
    private val _endDate   = MutableStateFlow(LocalDate.now())

    val startDate: StateFlow<LocalDate> = _startDate.asStateFlow()
    val endDate:   StateFlow<LocalDate> = _endDate.asStateFlow()

    // Объеденяем три потока: как только изменится id, startDate или endDate — запускаем загрузку
    init {
        combine(
            _accountId.filterNotNull(),
            _startDate,
            _endDate
        ) { accountId, start, end -> Triple(accountId, start, end) }
            .flatMapLatest { (accountId, start, end) ->
                loadTransactionsFlow(accountId, start, end)
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

    // Внешний API: установить accountId при навигации
    fun setAccountId(accountId: Int) {
        _accountId.value = accountId
    }

    // Внешний API: выбрать начальную дату
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
        end: LocalDate
    ): Flow<Resource<List<TransactionResponse>>> {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE
        val startStr = start.format(formatter)
        val endStr   = end.format(formatter)

        return getTransactionUseCase(
            accountId    = accountId,
            startDate    = startStr,
            endDate      = endStr
        )
    }
}

/*
@SuppressLint("NewApi")
@HiltViewModel
class ExpensesHistoryViewModel @Inject constructor(
    private val getTransactionUseCase: GetTransactionUseCase
) : ViewModel() {


    // UI-состояние списка
    // private val _uiState = MutableStateFlow<UiState<List<Transaction>>>(UiState.Loading)
    // val uiState: StateFlow<UiState<List<Transaction>>> = _uiState

    private val _state = mutableStateOf(TransactionListState())
    val state: State<TransactionListState> = _state

    init {
        getTransactions(state.value.)
    }

    private fun getTransactions() {
        getTransactionUseCase(
            accountId = ,
            startDate = ,
            endDate =
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value =
                }
                is Resource.Error -> {
                    _state.value =
                    )
                }
                is Resource.Loading -> {
                    _state.value =
                }
            }
        }.launchIn(viewModelScope)
    }

 /*   // Диапазон дат
    var startDate by mutableStateOf(LocalDate.now().withDayOfMonth(1))
        private set
    var endDate by mutableStateOf(LocalDate.now())
        private set

    // Флаги показа DatePickerDialog
    private val _showStartPicker = MutableStateFlow(false)
    val showStartPicker: StateFlow<Boolean> = _showStartPicker

    private val _showEndPicker = MutableStateFlow(false)
    val showEndPicker: StateFlow<Boolean> = _showEndPicker

    init {
        loadExpensesHistory()
    }

    fun loadExpensesHistory() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val all = repo.getTransactions()
                val expenses = all.filter { it.amount.toDoubleOrNull()?.let { it < 0 } ?: true }
                _uiState.value = UiState.Success(expenses)
            } catch (e: Throwable) {
                _uiState.value = UiState.Error(e)
            }
        }
    }

    // Открыть/закрыть диалоги
    fun openStartDatePicker() {
        _showStartPicker.value = true
    }

    fun openEndDatePicker() {
        _showEndPicker.value = true
    }

    fun dismissStartPicker() {
        _showStartPicker.value = false
    }

    fun dismissEndPicker() {
        _showEndPicker.value = false
    }

    // После выбора даты
    fun onStartDatePicked(accountId: Int, date: LocalDate) {
        startDate = date
        dismissStartPicker()
        loadExpensesHistory()
    }

    fun onEndDatePicked(accountId: Int, date: LocalDate) {
        endDate = date
        dismissEndPicker()
        loadExpensesHistory()
    }*/
}*/
