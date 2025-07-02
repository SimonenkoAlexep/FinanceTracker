package com.lsimanenka.financetracker.domain.viewmodel

import android.icu.util.Currency
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lsimanenka.financetracker.common.Resource
import com.lsimanenka.financetracker.data.CurrencyAssistant
import com.lsimanenka.financetracker.data.model.AccountResponse
import com.lsimanenka.financetracker.data.model.AccountUpdateRequest
import com.lsimanenka.financetracker.data.use_case.GetAccountByIdUseCase
import com.lsimanenka.financetracker.data.use_case.UpdateAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AccountEditState(
    val original: AccountResponse? = null,
    val draftBalance: String = "",
    val currency: String = "RUB",
    val name: String = "Мой счёт"
)

@HiltViewModel
class AccountEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase,
    private val currencyAssistant: CurrencyAssistant
) : ViewModel() {

    private val accountId = requireNotNull(savedStateHandle.get<Int>("accountId"))

    private val _state = mutableStateOf(AccountEditState())
    val state: State<AccountEditState> = _state

    init {
        Log.d("Result", "$accountId")
        getAccountByIdUseCase(accountId)
            .onEach { res ->
                Log.d("Result", "$res")
                if (res is Resource.Success) {
                    _state.value = AccountEditState(
                        original = res.data,
                        draftBalance = res.data!!.balance,
                        currency = res.data.currency,
                        name = res.data.name
                    )
                    currencyAssistant.setCurrency(res.data.currency)
                }
            }
            .launchIn(viewModelScope)
    }

    fun onBalanceChange(text: String) {
        _state.value = _state.value.copy(draftBalance = text)
    }
    fun onCurrencyChange(text: String) {
        _state.value = _state.value.copy(currency = text)
        currencyAssistant.setCurrency(text)
    }
    fun onNameChange(text: String) {
        _state.value = _state.value.copy(name = text)
    }

    fun save(onSaved: () -> Unit) {
        viewModelScope.launch {
            updateAccountUseCase(
                accountId,
                AccountUpdateRequest(
                    name     = _state.value.name,
                    balance  = _state.value.draftBalance,
                    currency = _state.value.currency
                )
            )
                .filterIsInstance<Resource.Success<Unit>>()
                .collect {
                    onSaved()
                }
        }
    }

}
