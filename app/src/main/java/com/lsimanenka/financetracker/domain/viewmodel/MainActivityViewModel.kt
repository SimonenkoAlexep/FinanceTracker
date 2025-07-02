package com.lsimanenka.financetracker.domain.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lsimanenka.financetracker.common.Resource
import com.lsimanenka.financetracker.data.AccountAssistant
import com.lsimanenka.financetracker.data.use_case.GetAccountByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    //private val getAccountByIdUseCase: GetAccountByIdUseCase,
    accountAssistant: AccountAssistant
) : ViewModel() {

    private val _state = mutableStateOf<Int?>(null)
    val state: State<Int?> = _state

    init {
        accountAssistant.selectedAccountId
            .filterNotNull().onEach { it -> _state.value = it }
            .launchIn(viewModelScope)
    }
}