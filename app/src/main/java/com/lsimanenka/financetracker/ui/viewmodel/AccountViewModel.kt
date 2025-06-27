package com.lsimanenka.financetracker.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lsimanenka.financetracker.common.Resource
import com.lsimanenka.financetracker.data.AccountAssistant
import com.lsimanenka.financetracker.data.model.AccountResponse
import com.lsimanenka.financetracker.data.use_case.GetAccountByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


data class AccountDetailState(
    val isLoading: Boolean = false,
    val account: AccountResponse? = null,
    val error: String = ""
)

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
    accountAssistant: AccountAssistant
) : ViewModel() {

    private val _state = mutableStateOf(AccountDetailState())
    val state: State<AccountDetailState> = _state

    init {
        // Когда ассистент отдаёт первый непустой accountId — сразу запросим детали
        accountAssistant.selectedAccountId
            .filterNotNull()
            .flatMapLatest { id ->
                getAccountByIdUseCase(id)
            }
            .onEach { result ->
                _state.value = when (result) {
                    is Resource.Loading -> AccountDetailState(isLoading = true)
                    is Resource.Success -> AccountDetailState(account = result.data)
                    is Resource.Error   -> AccountDetailState(error   = result.message.orEmpty())
                }
            }
            .launchIn(viewModelScope)
    }
}
