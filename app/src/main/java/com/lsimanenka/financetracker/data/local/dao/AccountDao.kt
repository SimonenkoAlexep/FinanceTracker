package com.lsimanenka.financetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.lsimanenka.financetracker.data.local.entity.AccountDbEntity
import com.lsimanenka.financetracker.data.local.entity.AccountWithDetails
import com.lsimanenka.financetracker.data.local.entity.StatItemDbEntity
import com.lsimanenka.financetracker.data.model.Account
import com.lsimanenka.financetracker.data.model.AccountUpdateRequest
import retrofit2.http.Body
import retrofit2.http.Path

@Dao
interface AccountDao {
    @Query("SELECT * FROM account\n")
    suspend fun getAccounts(): List<AccountDbEntity>

    @Transaction
    @Query("SELECT * FROM account WHERE id = :accountId")
    suspend fun getAccountById(accountId: Int): AccountWithDetails?

    //@Update
    //suspend fun updateAccountById(accountId: Int, request: AccountUpdateRequest): Account

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: AccountDbEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatItem(item: StatItemDbEntity)

    @Query("SELECT * FROM account WHERE id = :accountId")
    suspend fun getAccountEntityById(accountId: Long): AccountDbEntity?

    @Update
    suspend fun updateAccount(account: AccountDbEntity)
}