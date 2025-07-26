package com.lsimanenka.financetracker.data.repository.transaction
import android.util.Log
import androidx.annotation.VisibleForTesting
import com.lsimanenka.financetracker.data.local.dao.TransactionDao
import com.lsimanenka.financetracker.data.local.entity.TransactionDbEntity
import com.lsimanenka.financetracker.data.local.entity.TransactionWithDetails
import javax.inject.Inject

class TransactionLocalDataSource @Inject constructor(
    private val dao: TransactionDao
) {
    suspend fun getById(id: Int): TransactionWithDetails =
        dao.getTransactionById(id)
            ?: throw NoSuchElementException("Transaction $id not found in local DB")

    suspend fun getForAccountInPeriod(
        accountId: Int,
        startDate: String,
        endDate: String
    ): List<TransactionWithDetails> =
        dao.getTransactionsForAccountInPeriod(accountId, startDate, endDate)

    suspend fun updateTransaction(entity: TransactionDbEntity): TransactionWithDetails {
        val updated = dao.updateTransaction(entity)
        if (updated == 0) {
            Log.d("TRANSACTION SYNC UPDATING", "INSERTED")
            dao.insertTransaction(entity)
        }
        return dao.getTransactionById(entity.id.toInt())
            ?: throw IllegalStateException("Could not read back transaction ${entity.id}")
    }


    suspend fun createTransaction(entity: TransactionDbEntity): TransactionWithDetails {
        return updateTransaction(entity)
    }

    suspend fun deleteTransactionById(id: Int) {
        dao.deleteTransactionById(id)
    }
}
