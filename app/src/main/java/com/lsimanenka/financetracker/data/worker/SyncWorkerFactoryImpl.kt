package com.lsimanenka.financetracker.data.worker

import android.content.Context
import androidx.work.WorkerParameters
import com.lsimanenka.financetracker.data.repository.account.AccountRepository
import javax.inject.Inject
import javax.inject.Provider

class SyncWorkerFactoryImpl @Inject constructor(
    private val syncables: Set<@JvmSuppressWildcards Syncable>
) : SyncWorkerFactory {
    override fun create(appContext: Context, params: WorkerParameters): SyncWorker {
        return SyncWorker(appContext, params, syncables)
    }
}