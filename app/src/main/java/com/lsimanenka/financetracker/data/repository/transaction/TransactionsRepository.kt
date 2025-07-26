package com.lsimanenka.financetracker.data.repository.transaction

import com.lsimanenka.financetracker.data.model.Transaction
import com.lsimanenka.financetracker.data.model.TransactionRequest
import com.lsimanenka.financetracker.data.model.TransactionResponse
import okhttp3.Response

interface TransactionsRepository {
    suspend fun getTransactionsForAccountInPeriod(
        accountId: Int,
        startDate: String? = null,
        endDate: String? = null
    ): List<TransactionResponse>

    suspend fun createTransaction(request: TransactionRequest): Transaction

    suspend fun getTransactionById(accountId: Int): TransactionResponse

    suspend fun updateTransactionById(
        accountId: Int,
        request: TransactionRequest
    ): TransactionResponse

    suspend fun deleteTransactionById(accountId: Int)
    suspend fun syncTransactionsForAccountInPeriod(
        accountId: Int,
        startDate: String,
        endDate: String
    )


}
