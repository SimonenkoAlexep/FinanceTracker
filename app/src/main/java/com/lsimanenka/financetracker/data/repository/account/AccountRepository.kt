package com.lsimanenka.financetracker.data.repository.account

import com.lsimanenka.financetracker.data.model.Account
import com.lsimanenka.financetracker.data.model.AccountCreateRequest
import com.lsimanenka.financetracker.data.model.AccountHistoryResponse
import com.lsimanenka.financetracker.data.model.AccountResponse
import com.lsimanenka.financetracker.data.model.AccountUpdateRequest
import okhttp3.Response

interface AccountRepository {
    suspend fun getAccounts(): List<Account>

    suspend fun createAccount(
        request: AccountCreateRequest
    ): AccountResponse

    suspend fun getAccountById(
        accountId: Int
    ): AccountResponse

    suspend fun updateAccountById(
        accountId: Int,
        request: AccountUpdateRequest
    ): Account

    suspend fun deleteAccountById(
        accountId: Int
    )

    suspend fun getAccountByIdHistory(
        accountId: Int
    ): AccountHistoryResponse

    suspend fun syncAccounts()

}