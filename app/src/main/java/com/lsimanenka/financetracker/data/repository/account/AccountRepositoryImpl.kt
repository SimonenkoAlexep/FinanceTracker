package com.lsimanenka.financetracker.data.repository.account

import com.lsimanenka.financetracker.data.model.Account
import com.lsimanenka.financetracker.data.model.AccountCreateRequest
import com.lsimanenka.financetracker.data.model.AccountHistoryResponse
import com.lsimanenka.financetracker.data.model.AccountResponse
import com.lsimanenka.financetracker.data.model.AccountUpdateRequest
import com.lsimanenka.financetracker.data.network.AccountApi
import okhttp3.Response
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val api: AccountApi
) : AccountRepository {
    override suspend fun getAccounts(): List<Account> {
        return api.getAccounts()
    }

    override suspend fun createAccount(request: AccountCreateRequest): AccountResponse {
        return api.createAccount(request = request)
    }

    override suspend fun getAccountById(accountId: Int): AccountResponse {
        return api.getAccountById(accountId = accountId)
    }

    override suspend fun updateAccountById(accountId: Int, request: AccountUpdateRequest): Account {
        return api.updateAccountById(accountId = accountId, request = request)
    }

    override suspend fun deleteAccountById(accountId: Int) {
        return api.deleteAccountById(accountId = accountId)
    }

    override suspend fun getAccountByIdHistory(accountId: Int): AccountHistoryResponse {
        return api.getAccountByIdHistory(accountId = accountId)
    }

}