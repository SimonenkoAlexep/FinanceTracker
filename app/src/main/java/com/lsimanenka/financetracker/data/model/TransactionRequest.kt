package com.lsimanenka.financetracker.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TransactionRequest(
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String?
) 