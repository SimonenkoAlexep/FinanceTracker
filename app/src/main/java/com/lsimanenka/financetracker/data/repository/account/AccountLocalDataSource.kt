package com.lsimanenka.financetracker.data.repository.account

import android.annotation.SuppressLint
import android.util.Log
import com.lsimanenka.financetracker.data.local.dao.AccountDao
import com.lsimanenka.financetracker.data.local.entity.AccountDbEntity
import com.lsimanenka.financetracker.data.local.entity.AccountWithDetails
import com.lsimanenka.financetracker.data.local.entity.StatItemDbEntity
import com.lsimanenka.financetracker.data.mappers.toAccount
import com.lsimanenka.financetracker.data.model.Account
import com.lsimanenka.financetracker.data.model.AccountUpdateRequest
import java.time.Instant
import javax.inject.Inject

class AccountLocalDataSource @Inject constructor(
    private val dao: AccountDao
) {
    suspend fun getAll(): List<AccountDbEntity> =
        dao.getAccounts()

    suspend fun getById(id: Int): AccountWithDetails? =
        dao.getAccountById(id)

    /*suspend fun updateAccount(account: AccountDbEntity) {
        Log.d("UPDATEBEFORE" ,"${account.userId}, ${account.balance}")
        return dao.updateAccount(account)
    }**/

    suspend fun upsertAccount(entity: AccountDbEntity) {
        Log.d("UPDATEBEFORE" ,"${entity.id}, ${entity.balance}, ${entity.updatedAt}")
        dao.upsertAccount(entity)
    }

    suspend fun updateStats(stats: List<StatItemDbEntity>) =
        dao.updateStats(stats)

   /* suspend fun saveAccount(entity: AccountDbEntity) {
        dao.insert(entity)
    }

    suspend fun saveStats(items: List<StatItemDbEntity>) {
        items.forEach { dao.insertStatItem(it) }
    }*/

    @SuppressLint("NewApi")
    suspend fun updateAccountById(
        accountId: Int,
        request: AccountUpdateRequest
    ): Account {
        val existing = dao.getAccountEntityById(accountId.toLong())
            ?: throw NoSuchElementException("Account with id=$accountId not found")

        val now = Instant.now().toString()

        val updatedEntity = existing.copy(
            name      = request.name ?: existing.name,
            balance   = request.balance ?: existing.balance,
            currency  = request.currency ?: existing.currency,
            updatedAt = now
        )

        dao.upsertAccount(updatedEntity)

        return updatedEntity.toAccount()
    }


}
