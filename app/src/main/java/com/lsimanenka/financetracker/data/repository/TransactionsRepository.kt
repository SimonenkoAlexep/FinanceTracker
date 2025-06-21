package com.lsimanenka.financetracker.data.repository

import com.lsimanenka.financetracker.data.model.Transaction
import com.lsimanenka.financetracker.data.model.TransactionRequest
import com.lsimanenka.financetracker.data.model.TransactionResponse
import retrofit2.http.Path

interface TransactionsRepository {
    suspend fun getTransactionsForAccountInPeriod(
        accountId: Int,
        startDate: String? = null,
        endDate: String? = null
    ): List<TransactionResponse>

}
