package com.lsimanenka.financetracker.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.lsimanenka.financetracker.data.repository.account.AccountRepository
import javax.inject.Inject

class SyncWorker(
    context: Context,
    params: WorkerParameters,
    private val syncables: Set<Syncable>
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            syncables.forEach { it.sync() }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
