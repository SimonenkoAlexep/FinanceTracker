package com.lsimanenka.financetracker.data.repository

import com.lsimanenka.financetracker.data.model.TransactionResponse
import com.lsimanenka.financetracker.data.network.TransactionsApi
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
}
