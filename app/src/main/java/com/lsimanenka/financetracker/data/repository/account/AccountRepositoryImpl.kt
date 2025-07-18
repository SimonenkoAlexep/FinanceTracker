package com.lsimanenka.financetracker.data.repository.account

import com.lsimanenka.financetracker.data.NetworkChecker
import com.lsimanenka.financetracker.data.mappers.toAccount
import com.lsimanenka.financetracker.data.mappers.toAccountDbEntity
import com.lsimanenka.financetracker.data.mappers.toAccountResponse
import com.lsimanenka.financetracker.data.mappers.toDbEntity
import com.lsimanenka.financetracker.data.model.Account
import com.lsimanenka.financetracker.data.model.AccountCreateRequest
import com.lsimanenka.financetracker.data.model.AccountHistoryResponse
import com.lsimanenka.financetracker.data.model.AccountResponse
import com.lsimanenka.financetracker.data.model.AccountUpdateRequest
import com.lsimanenka.financetracker.data.network.AccountApi
import okhttp3.Response
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
                local.saveAccount(accountEntity)
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
            local.saveAccount(accountEntity.toAccountDbEntity())
            if (accountEntity.account.id != null) {
                local.saveStats(resp.incomeStats.map {
                    it.toDbEntity(
                        accountId = accountEntity.account.id,
                        type = "INCOME"
                    )
                })
                local.saveStats(resp.expenseStats.map {
                    it.toDbEntity(
                        accountId = accountEntity.account.id,
                        type = "EXPENSES"
                    )
                })
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
            local.saveAccount(accountEntity)

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

}