package com.lsimanenka.financetracker.data.local.dao

import androidx.room.*
import com.lsimanenka.financetracker.data.local.entity.TransactionDbEntity
import com.lsimanenka.financetracker.data.local.entity.TransactionWithDetails

@Dao
interface TransactionDao {

    /** читаем одну транзакцию с её связями */
    @Transaction
    @Query("SELECT * FROM `transaction` WHERE id = :transactionId")
    suspend fun getTransactionById(
        transactionId: Int
    ): TransactionWithDetails?

    /** читаем список транзакций за период (с их связями) */
    @Transaction
    @Query("""
    SELECT * FROM `transaction`
     WHERE account_id = :accountId
       AND substr(created_at,1,10) BETWEEN :startDate AND :endDate
  """)
    suspend fun getTransactionsForAccountInPeriod(
        accountId: Int,
        startDate: String,
        endDate: String
    ): List<TransactionWithDetails>

    /** вставляем или заменяем транзакцию, возвращаем её rowId */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(
        entity: TransactionDbEntity
    ): Long

    /** апдейт полей существующей транзакции, возвращает число изменённых строк */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTransaction(
        entity: TransactionDbEntity
    ): Int

    /** удаляем конкретную сущность */
    @Delete
    suspend fun deleteTransaction(
        entity: TransactionDbEntity
    )

    /** или удаляем по id */
    @Query("DELETE FROM `transaction` WHERE id = :transactionId")
    suspend fun deleteTransactionById(
        transactionId: Int
    ): Int
}
