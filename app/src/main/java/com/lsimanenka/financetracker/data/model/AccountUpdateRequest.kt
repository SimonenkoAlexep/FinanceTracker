package com.lsimanenka.financetracker.data.model


data class AccountUpdateRequest(
    val name: String,
    val balance: String,
    val currency: String
) 