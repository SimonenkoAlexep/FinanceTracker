package com.lsimanenka.financetracker.data.repository.transaction

import android.util.Log
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
        Log.d("HELP", "${request.amount}")
        return api.createTransaction(request = request)
    }

    override suspend fun getTransactionById(accountId: Int): TransactionResponse {
        return api.getTransactionById(accountId = accountId)
    }

    override suspend fun updateTransactionById(
        accountId: Int,
        request: TransactionRequest
    ): TransactionResponse {
        return api.updateTransactionById(accountId = accountId, request = request)
    }

    override suspend fun deleteTransactionById(accountId: Int) {
        return api.deleteTransactionById(accountId = accountId)
    }
}
