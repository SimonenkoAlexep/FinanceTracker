package com.lsimanenka.financetracker.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.lsimanenka.financetracker.data.repository.account.AccountRepository

class SyncWorker(
    appContext: Context,
    params: WorkerParameters,
    private val repository: AccountRepository
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            repository.syncAccounts()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}