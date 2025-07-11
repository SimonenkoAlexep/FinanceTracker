package com.lsimanenka.financetracker.domain.viewmodel

import android.icu.util.Currency
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lsimanenka.financetracker.common.Resource
import com.lsimanenka.financetracker.data.AccountAssistant
import com.lsimanenka.financetracker.data.CurrencyAssistant
import com.lsimanenka.financetracker.data.model.AccountResponse
import com.lsimanenka.financetracker.data.model.AccountUpdateRequest
import com.lsimanenka.financetracker.data.use_case.GetAccountByIdUseCase
import com.lsimanenka.financetracker.data.use_case.UpdateAccountUseCase
//import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AccountEditState(
    val original: AccountResponse? = null,
    val draftBalance: String = "",
    val currency: String = "",
    val name: String = "Мой счёт"
)

//@HiltViewModel
class AccountEditViewModel @Inject constructor(
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase,
    private val currencyAssistant: CurrencyAssistant,
    accountAssistant: AccountAssistant,
) : ViewModel() {

    private var accountId: Int? = null

    private val _state = mutableStateOf(AccountEditState())
    val state: State<AccountEditState> = _state

    init {


        Log.d("Result", "$accountId")
        viewModelScope.launch {
            val id = accountAssistant.selectedAccountId
                .filterNotNull()
                .first()

            accountId = id

            getAccountByIdUseCase(id)
                .onEach { result ->
                    if (result is Resource.Success) {
                        _state.value = AccountEditState(
                            original     = result.data,
                            draftBalance = result.data!!.balance,
                            currency     = result.data.currency,
                            name         = result.data.name
                        )
                        currencyAssistant.setCurrency(result.data.currency)
                        Log.d("Result after", "$accountId, ${_state.value.currency}")
                    }
                }
                .launchIn(viewModelScope)
        }

    }

    fun onBalanceChange(text: String) {
        _state.value = _state.value.copy(draftBalance = text)
    }

    fun onCurrencyChange(text: String) {
        _state.value = _state.value.copy(currency = text)
        Log.d("onCurrencyChange", "$accountId, ${_state.value.currency}")
        //currencyAssistant.setCurrency(text)
    }

    fun onNameChange(text: String) {
        _state.value = _state.value.copy(name = text)
    }

    fun save(onSaved: () -> Unit) {
        Log.d("Befoooooore setCurrency", "$accountId, ${currencyAssistant.selectedCurrency.value}, ${_state.value.currency}")
        val id = accountId ?: return
        viewModelScope.launch {
            updateAccountUseCase(
                id,
                AccountUpdateRequest(
                    name     = _state.value.name,
                    balance  = _state.value.draftBalance,
                    currency = _state.value.currency
                )
            )
                .filterIsInstance<Resource.Success<Unit>>()
                .collect {
                    Log.d("Before setCurrency", "$accountId, ${currencyAssistant.selectedCurrency.value}, ${_state.value.currency}")
                    currencyAssistant.setCurrency(_state.value.currency)
                    onSaved()
                    Log.d("After setCurrency", "$accountId, ${currencyAssistant.selectedCurrency.value}, ${_state.value.currency}")
                }
        }
    }

}
