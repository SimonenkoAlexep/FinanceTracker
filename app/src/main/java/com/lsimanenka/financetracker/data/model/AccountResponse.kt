package com.lsimanenka.financetracker.data.model


data class AccountResponse(
    val id: Int,
    val name: String,
    var balance: String,
    val currency: String,
    val incomeStats: List<StatItem>,
    val expenseStats: List<StatItem>,
    val createdAt: String,
    val updatedAt: String
) 