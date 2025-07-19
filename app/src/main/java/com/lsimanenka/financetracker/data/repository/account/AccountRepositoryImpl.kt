package com.lsimanenka.financetracker.data.repository.account

import android.util.Log
import com.lsimanenka.financetracker.data.NetworkChecker
import com.lsimanenka.financetracker.data.mappers.toAccount
import com.lsimanenka.financetracker.data.mappers.toAccountDbEntity
import com.lsimanenka.financetracker.data.mappers.toAccountResponse
import com.lsimanenka.financetracker.data.mappers.toDbEntity
import com.lsimanenka.financetracker.data.mappers.toUpdateRequest
import com.lsimanenka.financetracker.data.model.Account
import com.lsimanenka.financetracker.data.model.AccountCreateRequest
import com.lsimanenka.financetracker.data.model.AccountHistoryResponse
import com.lsimanenka.financetracker.data.model.AccountResponse
import com.lsimanenka.financetracker.data.model.AccountUpdateRequest
import com.lsimanenka.financetracker.data.network.AccountApi
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val api: AccountApi,
    private val local: AccountLocalDataSource,
    private val networkChecker: NetworkChecker
) : AccountRepository {
    override suspend fun getAccounts(): List<Account> {
        return if (networkChecker.isOnline()) {
            val remoteList = api.getAccounts()

            remoteList.forEach { resp ->
                val accountEntity = resp.toDbEntity()
                local.upsertAccount(accountEntity)
            }

            remoteList

        } else {
            local.getAll().map { accounts ->
                accounts.toAccount()
            }
        }
    }

    override suspend fun getAccountById(accountId: Int): AccountResponse {
        return if (networkChecker.isOnline()) {
            val resp = api.getAccountById(accountId)

            val accountEntity = resp.toDbEntity()
            local.upsertAccount(accountEntity.toAccountDbEntity())
            if (accountEntity.account.id != null) {

                //local.updateStats(resp.incomeStats)

                local.updateStats(
                    resp.incomeStats.map {
                        it.toDbEntity(
                            accountId = accountEntity.account.id,
                            type = "INCOME"
                        )
                    } + resp.expenseStats.map {
                        it.toDbEntity(
                            accountId = accountEntity.account.id,
                            type = "EXPENSES"
                        )
                    }
                )
            }
            resp
        } else {
            local.getById(accountId)
                ?.toAccountResponse()
                ?: throw NoSuchElementException("Account $accountId not found in local DB")
        }
    }

    override suspend fun createAccount(request: AccountCreateRequest): AccountResponse {
        return api.createAccount(request = request)
    }

    override suspend fun updateAccountById(
        accountId: Int,
        request: AccountUpdateRequest
    ): Account {
        return if (networkChecker.isOnline()) {
            val res = api.updateAccountById(accountId = accountId, request = request)

            val accountEntity = res.toDbEntity()
            local.upsertAccount(accountEntity)

            return res
        } else {
            local.updateAccountById(accountId, request)
        }
    }

//    override suspend fun updateAccountById(accountId: Int, request: AccountUpdateRequest): Account {
//        return api.updateAccountById(accountId = accountId, request = request)
//    }

    override suspend fun deleteAccountById(accountId: Int) {
        return api.deleteAccountById(accountId = accountId)
    }

    override suspend fun getAccountByIdHistory(accountId: Int): AccountHistoryResponse {
        return api.getAccountByIdHistory(accountId = accountId)
    }

    override suspend fun syncAccounts() {
        val localAcc = local.getAll().first().toAccount()
        val remote = api.getAccounts().first()
        Log.d("LocalAcc+remote", "id: ${localAcc.id}, userId: ${localAcc.userId}, remId: ${remote.id}, rusId: ${remote.userId}")
        Log.d("Balance" ,"loc: ${localAcc.balance}, rem: ${remote.balance}")
        /* local.forEach { localEnt ->
             val remote = remote.find { it.id.toLong() == localEnt.id }

             if (localEnt.updatedAt > remote!!.updatedAt) {
                 api.updateAccountById(
                     localEnt.id!!.toInt(),
                     localEnt.toUpdateRequest()
                 )
             }

         }*/
        Log.d("updatedAT", "L: ${localAcc.updatedAt}, R: ${remote.updatedAt}")
        if (localAcc.updatedAt > remote.updatedAt) {
            Log.d("BalanceBEFORE1" ,"loc: ${localAcc.balance}, rem: ${remote.balance}")
            Log.d("REMOTE1" ,"${remote.userId}, ${remote.balance}")
            val updated = api.updateAccountById(
                localAcc.userId,
                AccountUpdateRequest(localAcc.name, localAcc.balance, localAcc.currency)
            )
            Log.d("UPDATED" ,"${updated.userId}, ${updated.balance}, ${updated.updatedAt}")
            local.upsertAccount(updated.toDbEntity())
            Log.d("BalanceAFTER1" ,"loc: ${localAcc.balance}, rem: ${remote.balance}")
        } else {
            Log.d("BalanceBEFORE" ,"loc: ${localAcc.balance}, rem: ${remote.balance}")
            Log.d("REMOTE" ,"${remote.userId}, ${remote.balance}")
            local.upsertAccount(remote.toDbEntity())
            Log.d("BalanceAFTER" ,"loc: ${localAcc.balance}, rem: ${remote.balance}")
        }


        /* remote.forEach { resp ->
             val localEnt = local.find { it.id!!.toLong() == resp.id.toLong() }
             if (localEnt == null || resp.updatedAt > localEnt.updatedAt) {
                 val ent = resp.toDbEntity()
                 this.local.saveAccount(ent)
                 /*resp.incomeStats.plus(resp.expenseStats)
                     .map { it.toDbEntity(ent.id) }
                     .also { local.saveStats(it) }*/
             }
         }*/
    }

}