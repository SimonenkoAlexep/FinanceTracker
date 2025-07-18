package com.lsimanenka.financetracker.data.worker

import android.content.Context
import androidx.work.WorkerParameters

interface SyncWorkerFactory {
    fun create(
        appContext: Context,
        params: WorkerParameters
    ): SyncWorker
}