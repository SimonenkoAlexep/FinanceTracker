package com.lsimanenka.financetracker.data.repository.transaction

import com.lsimanenka.financetracker.data.model.TransactionRequest
import com.lsimanenka.financetracker.data.model.TransactionResponse
import com.lsimanenka.financetracker.data.network.TransactionsApi
import okhttp3.Response
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
    private val api: TransactionsApi
) : TransactionsRepository {

    override suspend fun getTransactionsForAccountInPeriod(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<TransactionResponse> {
        return api.getTransactionsForAccountInPeriod(
            accountId = accountId,
            startDate = startDate,
            endDate = endDate
        )

    }

    override suspend fun createTransaction(request: TransactionRequest): TransactionResponse {
        return api.createTransaction(request = request)
    }

    override suspend fun getTransactionById(accoountId: Int): TransactionResponse {
        return api.getTransactionById(accoountId = accoountId)
    }

    override suspend fun updateTransactionById(
        accoountId: Int,
        request: TransactionRequest
    ): TransactionResponse {
        return api.updateTransactionById(accoountId = accoountId, request = request)
    }

    override suspend fun deleteTransactionById(accoountId: Int): Response {
        return api.deleteTransactionById(accoountId = accoountId)
    }
}
