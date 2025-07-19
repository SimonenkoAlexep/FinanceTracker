package com.lsimanenka.financetracker.data.repository.transaction

import android.annotation.SuppressLint
import android.util.Log
import com.lsimanenka.financetracker.data.NetworkChecker
import com.lsimanenka.financetracker.data.local.dao.TransactionDao
import com.lsimanenka.financetracker.data.local.entity.TransactionDbEntity
import com.lsimanenka.financetracker.data.local.entity.TransactionWithDetails
import com.lsimanenka.financetracker.data.mappers.toDbEntity
import com.lsimanenka.financetracker.data.mappers.toResponse
import com.lsimanenka.financetracker.data.model.TransactionRequest
import com.lsimanenka.financetracker.data.model.TransactionResponse
import com.lsimanenka.financetracker.data.network.TransactionsApi
import com.lsimanenka.financetracker.data.repository.transaction.TransactionLocalDataSource
import java.time.Instant
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
    private val api: TransactionsApi,
    private val local: TransactionLocalDataSource,
    private val networkChecker: NetworkChecker
) : TransactionsRepository {

    override suspend fun getTransactionsForAccountInPeriod(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<TransactionResponse> {
        return if (networkChecker.isOnline()) {
            // 1) fetch from network
            val remoteList = api.getTransactionsForAccountInPeriod(
                accountId = accountId,
                startDate = startDate,
                endDate   = endDate
            )

            // 2) store each in local DB
            remoteList.forEach { resp ->
                val entity = resp.toDbEntity()
                local.updateTransaction(entity)
            }

            // 3) return fresh network data
            remoteList
        } else {
            // offline: read from local DB and map to response
            local.getForAccountInPeriod(
                accountId = accountId,
                startDate = startDate ?: "",
                endDate   = endDate   ?: ""
            ).map { it.toResponse() }
        }
    }

    @SuppressLint("NewApi")
    override suspend fun createTransaction(
        request: TransactionRequest
    ): TransactionResponse {
        return if (networkChecker.isOnline()) {
            // network create
            val resp = api.createTransaction(request)
            // persist locally
            local.updateTransaction(resp.toDbEntity())
            resp
        } else {
            val requestNew = TransactionDbEntity(
                id = (System.currentTimeMillis()).toLong(),
                accountId = request.accountId.toLong(),
                categoryId = request.categoryId.toLong(),
                amount = request.amount,
                transactionDate = request.transactionDate,
                comment = request.comment,
                createdAt = Instant.now().toString(),
                updatedAt = Instant.now().toString()
            )
            local.createTransaction(requestNew).toResponse()
        }
    }

    override suspend fun getTransactionById(
        transactionId: Int
    ): TransactionResponse {
        return if (networkChecker.isOnline()) {
            // fetch and persist
            val resp = api.getTransactionById(transactionId)
            local.updateTransaction(resp.toDbEntity())
            resp
        } else {
            // offline lookup
            local.getById(transactionId)
                .toResponse()
        }
    }

    @SuppressLint("NewApi")
    override suspend fun updateTransactionById(
        transactionId: Int,
        request: TransactionRequest
    ): TransactionResponse {
        return if (networkChecker.isOnline()) {
            // network update
            val resp = api.updateTransactionById(transactionId, request)
            // persist updated record
            local.updateTransaction(resp.toDbEntity())
            resp
        } else {
            // offline update

            val ent = TransactionDbEntity(
                id = transactionId.toLong(),
                accountId = request.accountId.toLong(),
                categoryId = request.categoryId.toLong(),
                amount = request.amount,
                transactionDate = request.transactionDate,
                comment = request.comment,
                createdAt = Instant.now().toString(),
                updatedAt = Instant.now().toString()
            )

            local.updateTransaction(ent).toResponse()
        }
    }

    override suspend fun deleteTransactionById(
        transactionId: Int
    ) {
        if (networkChecker.isOnline()) {
            api.deleteTransactionById(transactionId)
        }
        // always delete locally
        local.deleteTransactionById(transactionId)
    }

    /*override suspend fun syncTransactions() {
        // if you need full sync, implement same pattern as syncAccounts(),
        // but here focus is on per-method offline-first behavior
        Log.d("TransactionsRepo", "sync not implemented")
    }*/
}
