package com.lsimanenka.financetracker.data.repository.transaction

import com.lsimanenka.financetracker.data.model.TransactionRequest
import com.lsimanenka.financetracker.data.model.TransactionResponse
import okhttp3.Response

interface TransactionsRepository {
    suspend fun getTransactionsForAccountInPeriod(
        accountId: Int,
        startDate: String? = null,
        endDate: String? = null
    ): List<TransactionResponse>

    suspend fun createTransaction(request: TransactionRequest): TransactionResponse

    suspend fun getTransactionById(accountId: Int): TransactionResponse

    suspend fun updateTransactionById(
        accoountId: Int,
        request: TransactionRequest
    ): TransactionResponse

    suspend fun deleteTransactionById(accoountId: Int): Response


}
