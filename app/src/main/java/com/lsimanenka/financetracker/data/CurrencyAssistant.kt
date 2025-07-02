package com.lsimanenka.financetracker.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyAssistant @Inject constructor() {
    private val _selectedCurrency = MutableStateFlow("₽")
    val selectedCurrency: StateFlow<String> = _selectedCurrency

    fun setCurrency(currency: String) {
        _selectedCurrency.value = currency
    }
}