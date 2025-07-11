package com.lsimanenka.financetracker.domain.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lsimanenka.financetracker.common.Resource
import com.lsimanenka.financetracker.data.AccountAssistant
import com.lsimanenka.financetracker.data.CurrencyAssistant
import com.lsimanenka.financetracker.data.model.TransactionRequest
import com.lsimanenka.financetracker.data.use_case.DeleteTransactionByIdUseCase
import com.lsimanenka.financetracker.data.use_case.GetTransactionByIdUseCase
import com.lsimanenka.financetracker.data.use_case.PostTransactionUseCase
import com.lsimanenka.financetracker.data.use_case.UpdateTransactionByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@SuppressLint("NewApi")
data class TransactionState(
    val loading: Boolean = false,
    val error: String? = null,
    val accountId: Int? = null,
    val categoryId: Int? = null,
    val amount: String = "",
    val date: LocalDate = LocalDate.now(),
    val time: LocalTime = LocalTime.now(),
    val description: String? = null
)

class TransactionViewModel @Inject constructor(
    private val postTransactionUseCase: PostTransactionUseCase,
    private val updateTransactionByIdUseCase: UpdateTransactionByIdUseCase,
    private val deleteTransactionByIdUseCase: DeleteTransactionByIdUseCase,
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val accountAssistant: AccountAssistant,
    currencyAssistant: CurrencyAssistant
) : ViewModel() {


    private var isEditMode = false
    private var transactionIdGlob: Int? = null
    private val _uiState = MutableStateFlow(TransactionState())
    val uiState: StateFlow<TransactionState> = _uiState

    private val accountIdFlow = accountAssistant.selectedAccountId

    val currency = currencyAssistant.selectedCurrency

    private val _isIncome = MutableStateFlow<Boolean?>(null)

    @SuppressLint("NewApi")
    fun init(transactionId: Int?) {
        transactionIdGlob = transactionId
        isEditMode = transactionId != null

        viewModelScope.launch {
            if (isEditMode) {
                _uiState.update { it.copy(loading = true) }
                getTransactionByIdUseCase(transactionId!!).collect { res ->
                    if (res is Resource.Success) {
                        val t = res.data!!
                        val instant = Instant.parse(t.transactionDate)
                        val dateTime =
                            LocalDateTime.ofInstant(instant, ZoneOffset.UTC)

                        _uiState.value = TransactionState(
                            accountId   = t.account.id,
                            categoryId  = t.category.id,
                            amount      = t.amount.toString(),
                            date        = dateTime.toLocalDate(),
                            time        = dateTime.toLocalTime(),
                            description = t.comment
                        )
                    }
                }
            } else {
                // режим CREATE
                accountAssistant.selectedAccountId
                    .filterNotNull()
                    .firstOrNull()
                    ?.let { defaultId ->
                        _uiState.update { it.copy(accountId = defaultId) }
                    }
            }
        }
    }

    fun onAmountChange(v: String) = _uiState.update { it.copy(amount = v) }

    fun onCategoryIdChange(v: Int) = _uiState.update { it.copy(categoryId = v) }

    fun onDateChange(v: LocalDate) = _uiState.update { it.copy(date = v) }

    fun onTimeChange(v: LocalTime) = _uiState.update { it.copy(time = v) }

    fun onDescriptionChange(v: String) = _uiState.update { it.copy(description = v) }


    @SuppressLint("NewApi")
    fun save(onComplete: () -> Unit) {
        viewModelScope.launch {
            val s = _uiState.value
            val accountId = s.accountId ?: return@launch
            val categoryId = s.categoryId ?: return@launch
            val amount = s.amount.toDoubleOrNull() ?: return@launch

            Log.d("Saving", "${_uiState.value.date}")

            val localDateTime = LocalDateTime.of(s.date, s.time)
            val instant = localDateTime.toInstant(ZoneOffset.UTC).toString()

            val request = TransactionRequest(
                accountId = accountId,
                categoryId = categoryId,
                amount = amount.toString(),
                transactionDate = instant,
                comment = s.description ?: ""
            )
            Log.d("ВВВВВВВВ", "${request.accountId}, ${request.categoryId}, ${request.amount}, ${request.transactionDate}, ${request.comment}")

            val flow = if (isEditMode) transactionIdGlob?.let {
                updateTransactionByIdUseCase(
                    it,
                    request
                )
            } else {
                Log.d("Saving", "${request.categoryId}, ${request.accountId}")
                postTransactionUseCase(request)
            }

            if (flow != null) {
                flow.onEach { res ->
                    when (res) {
                        is Resource.Loading -> {
                            _uiState.update { it.copy(loading = true, error = null) }
                        }

                        is Resource.Success -> {
                            Log.d("Success", "${request.categoryId}, ${request.accountId}")
                            _uiState.update { it.copy(loading = false) }
                            onComplete()
                        }

                        is Resource.Error -> {
                            Log.e("TransactionViewModel", "Save failed: ${res.message}")
                            _uiState.update {
                                it.copy(
                                    loading = false,
                                    error = res.message.orEmpty()
                                )
                            }
                        }
                    }
                }
                    .launchIn(viewModelScope)
            }
        }
    }


    fun delete(onComplete: () -> Unit) {
        if (!isEditMode) return

        viewModelScope.launch {
            val id = _uiState.value.accountId
            if (id == null) return@launch

            deleteTransactionByIdUseCase(id).onEach { res ->
                when (res) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(loading = true, error = null) }
                    }

                    is Resource.Success -> {
                        _uiState.update { it.copy(loading = false) }
                        onComplete()
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                loading = false,
                                error = res.message.orEmpty()
                            )
                        }
                    }
                }
            }
                .launchIn(viewModelScope)
        }
    }
}

