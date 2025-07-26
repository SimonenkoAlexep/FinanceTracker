package com.lsimanenka.financetracker.data.worker

import android.annotation.SuppressLint
import com.lsimanenka.financetracker.data.AccountAssistant
import com.lsimanenka.financetracker.data.repository.account.AccountRepository
import com.lsimanenka.financetracker.data.repository.transaction.TransactionsRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class AccountSyncable @Inject constructor(
    private val repo: AccountRepository
) : Syncable {
    override suspend fun sync() = repo.syncAccounts()
}

class TransactionSyncable @Inject constructor(
    private val repo: TransactionsRepository,
    private val accountAssistant: AccountAssistant
) : Syncable {

    @SuppressLint("NewApi")
    override suspend fun sync() {
        val accountId = accountAssistant.selectedAccountId.value

        val today = LocalDate.now()
        val start = today.minusDays(7).format(DateTimeFormatter.ISO_DATE)
        val end = today.format(DateTimeFormatter.ISO_DATE)

        if (accountId != null) {
            repo.syncTransactionsForAccountInPeriod(accountId, start, end)
        }
    }
}