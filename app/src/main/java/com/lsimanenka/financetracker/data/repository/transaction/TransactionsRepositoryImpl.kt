package com.lsimanenka.financetracker.data.repository.transaction

import android.annotation.SuppressLint
import android.util.Log
import com.lsimanenka.financetracker.data.NetworkChecker
import com.lsimanenka.financetracker.data.local.dao.TransactionDao
import com.lsimanenka.financetracker.data.local.entity.TransactionDbEntity
import com.lsimanenka.financetracker.data.local.entity.TransactionWithDetails
import com.lsimanenka.financetracker.data.mappers.toDbEntity
import com.lsimanenka.financetracker.data.mappers.toRequest
import com.lsimanenka.financetracker.data.mappers.toResponse
import com.lsimanenka.financetracker.data.mappers.toTransaction
import com.lsimanenka.financetracker.data.mappers.toTransactionWithDetails
import com.lsimanenka.financetracker.data.model.Transaction
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
                endDate = endDate
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
                endDate = endDate ?: ""
            ).map { it.toResponse() }
        }
    }

    @SuppressLint("NewApi")
    override suspend fun createTransaction(
        request: TransactionRequest
    ): Transaction {
        return if (networkChecker.isOnline()) {
            Log.d("UPDATE Transaction", "${request.accountId}")
            val resp = api.createTransaction(request)

            Log.d("UPDATE Transaction", "${resp.accountId}")
            local.updateTransaction(resp.toDbEntity())
            resp
        } else {
            val requestNew = TransactionDbEntity(
                id = (System.currentTimeMillis()).toInt().toLong(),
                accountId = request.accountId.toLong(),
                categoryId = request.categoryId.toLong(),
                amount = request.amount,
                transactionDate = request.transactionDate,
                comment = request.comment,
                createdAt = Instant.now().toString(),
                updatedAt = Instant.now().toString()
            )
            val trans = local.createTransaction(requestNew)
            return trans.toTransaction()
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

    override suspend fun syncTransactionsForAccountInPeriod(
        accountId: Int,
        startDate: String,
        endDate: String
    ) {
        val localList = local.getForAccountInPeriod(accountId, startDate, endDate)

        val remoteList = api.getTransactionsForAccountInPeriod(accountId, startDate, endDate)

        val remoteMap: MutableMap<Int, TransactionResponse> =
            remoteList.associateBy { it.id }.toMutableMap()
        if (localList.isNotEmpty()) {
            localList.forEach { localEnt ->
                //val remoteEnt = remoteMap[localEnt.transaction.id.toInt()]?.toTransactionWithDetails()
               /* Log.d(
                    "TRANSACTION SYNC",
                    "${localEnt.transaction.id} ${remoteEnt?.transaction?.id}"
                )*/


                val oldId = localEnt.transaction.id.toInt()
                val remoteEnt = remoteMap[oldId]

                if (remoteEnt == null) {
                    val trans = api.createTransaction(localEnt.toRequest())
                    local.updateTransaction(trans.toDbEntity())
                    local.deleteTransactionById(oldId)

                } else if (localEnt.transaction.updatedAt > remoteEnt.updatedAt) {
                    api.updateTransactionById(oldId, localEnt.toRequest())
                    local.updateTransaction(localEnt.toTransaction().toDbEntity())
                } else {
                    local.updateTransaction(remoteEnt.toDbEntity())
                }

                remoteMap.remove(oldId)
            }

            // 2) Добавляем те, что есть только на сервере
            remoteMap.values.forEach { onlyRemote ->
                local.updateTransaction(onlyRemote.toDbEntity())
            }


            /*  when {

                  remoteEnt == null -> {
                      val trans = api.createTransaction(localEnt.toRequest())
                      val localTransCopy = localEnt
                      localTransCopy.transaction.id = trans.id.toLong()
                      Log.d("TRANSACTION SYNC ID", "${trans.id}")
                      val updTrans = local.updateTransaction(localTransCopy.toTransaction().toDbEntity())
                      Log.d("TRANSACTION SYNC UPD", "${updTrans.transaction.id}")
                      local.deleteTransactionById(localEnt.transaction.id.toInt())
                      Log.d("TRANSACTION SYNC AFTER", "${localEnt.transaction.id} ${remoteEnt?.transaction?.id}")
                  }

                  localEnt.transaction.updatedAt > remoteEnt.transaction.updatedAt -> {
                      val resp = api.updateTransactionById(localEnt.transaction.id.toInt(), localEnt.toRequest())
                      val localTransCopy = localEnt
                      localTransCopy.transaction.id = resp.id.toLong()
                      local.updateTransaction(localTransCopy.toTransaction().toDbEntity())
                      local.deleteTransactionById(localEnt.transaction.id.toInt())
                  }

                  else -> {
                  }
              }
              Log.d("TRANSACTION SYNC REMOVE", "${remoteMap[localEnt.transaction.id.toInt()]}")
              remoteMap.remove(localEnt.transaction.id.toInt())
          }

          Log.d("TRANSACTION SYNC", "${remoteMap.values} ")

          remoteMap.values.forEach { newRemote ->
              local.updateTransaction(newRemote.toDbEntity())
          }*/
        }
    }
}
